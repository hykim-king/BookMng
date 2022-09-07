package com.pcwk.ehr.library.dao;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.LoggerManger;
import com.pcwk.ehr.cmn.WorkDiv;

import com.pcwk.ehr.library.domain.Book;
import com.pcwk.ehr.library.domain.Search;

public class BookDao implements WorkDiv<Book>, LoggerManger {
	// BookDao객체 생성시 파일에 있는 내용을 읽어 ArrayList<DTO>담는다.
	// 종료시 data를 파일에 기록!

	public static ArrayList<Book> data = new ArrayList<Book>();

	// 저장파일 경로
	public final static String SAVE_FILE_PATH = "C:\\Users\\ITSC\\git\\BookMng\\boot.csv";

	public final static String SAVE_JSON_FILE_PATH = "C:\\Users\\ITSC\\git\\BookMng\\book.gson";
	
	
	public BookDao() {
		readFile(SAVE_FILE_PATH);
		//readFileJson(SAVE_JSON_FILE_PATH);
		LOG.debug("data.size():" + BookDao.data.size());

		for (Book book : BookDao.data) {
			LOG.debug(book.toString());
		}
	}

	/**
	 * 파일 읽기
	 * 
	 * @param  filePath String
	 * @return int
	 */
	public int readFile(String filePath) {
		int flag = 0;

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(SAVE_FILE_PATH);
			br = new BufferedReader(fr);

			int data = 0;
			String line = "";// 데이터를 line단위로 관리
			while ((line = br.readLine()) != null) {
				LOG.debug(line);
				// 9791163031222,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문,이고잉 고경희,소프트웨어 공학,2019년 12월 06일,1
				String inArray[] = line.split(",");

				// 대여가능 여부
				// 1 -> true
				// 0 -> false
				boolean avaliable = inArray[5].equals("1") ? true : false;

				Book book = new Book(inArray[0], inArray[1], inArray[2], inArray[3], inArray[4], avaliable);
				// ArrayList에 추가
				BookDao.data.add(book);

			}

			if (BookDao.data.size() > 0)
				flag++;

		} catch (IOException e) {
			LOG.debug("IOException:" + e.getMessage());
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.debug("IOException:" + e.getMessage());
				}
			}
		}

		return flag;
	}

	/**
	 * JSON파일 읽기
	 * @param  filePath String
	 * @return int
	 */
	public int readFileJson(String filePath) {
		Gson gson = new Gson();
		int flag = 0;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(SAVE_FILE_PATH);
			br = new BufferedReader(fr);

			int data = 0;
			String line = "";// 데이터를 line단위로 관리
			StringBuilder  sb=new StringBuilder();
			while ((line = br.readLine()) != null) {
				LOG.debug(line);
				sb.append(line);

			}
			Type type = new TypeToken<ArrayList<Book>>() {}.getType();
			BookDao.data = gson.fromJson(sb.toString(), type);
	
			flag =BookDao.data.size()>0?1:0;
			for(Book p :BookDao.data) {
				System.out.println(p);
			}
		} catch (IOException e) {
			LOG.debug("IOException:" + e.getMessage());
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.debug("IOException:" + e.getMessage());
				}
			}
		}
		
		return flag;
	}
	
	public int writeFileJson(String filePath) {
		int flag = 0;
		Gson gson = new Gson();
		try (FileWriter fw = new FileWriter(filePath); 
			 BufferedWriter bw = new BufferedWriter(fw);) {

			 String jsonArrayString = gson.toJson(BookDao.data);
			 
			 bw.write(jsonArrayString);

		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
		}
		
		return flag;
	}

	
	

	
	/**
	 * 파일 저장
	 * @param  filePath String
	 * @param  search String
	 * @return String
	 */
	public String writeNaverJSonFile(String filePath,String search) {
		int flag = 1;
		
		String clientId = "PF2hZcj7_cmVHc8unUOn";
		String clientSecret = "Z2F9wQ0NVO";
		StringBuilder sb=new StringBuilder();
		BufferedReader br = null;
		try (FileWriter fw = new FileWriter(BookDao.SAVE_JSON_FILE_PATH);
				BufferedWriter bw = new BufferedWriter(fw);) {
			String text = URLEncoder.encode(search, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text + "&display=20";

			URL url = new URL(apiURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			int responseCode = con.getResponseCode();// 접속 상태 코드
			LOG.debug("responseCode:" + responseCode);

			if (responseCode == 200) {// 접속과, 조회 성공
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {// 접속 오류
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			LOG.debug("blog검색:" + text);

			String inputLine = "";// 데이터를 1줄씩 read
			while ((inputLine = br.readLine()) != null) {// 더이상 읽을 데이터가 없으면 null return
				bw.write(inputLine+"\n");
				sb.append(inputLine+"\n");
				LOG.debug(inputLine);
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flag = 0;
			e.printStackTrace();
		} finally {
			if(null !=br) {
				// stream을 닫기!
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		
		return sb.toString().trim();
	}
	
	/**
	 * 파일 저장!
	 * 
	 * @param  filePath String
	 * @return int
	 */
	public int writeFile(String filePath) {
		int flag = 0;
		// ArrayList<Book> -> File로 기록
		//
		// try -- with --resource
		try (FileWriter fw = new FileWriter(filePath); 
			 BufferedWriter bw = new BufferedWriter(fw);) {

			// 9791163031222,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문,이고잉 고경희,소프트웨어 공학,2019년 12월 06일,1
			for (Book book : BookDao.data) {

				String delim = ",";
				int avaliable = (book.isBorrow() == true) ? 1 : 0;

				String outLine = book.getIsbn() + delim + book.getTitle() + delim + book.getAuthor() + delim
						+ book.getGenre() + delim + book.getPublicationDate() + delim + book.isBorrow() + "\n";
				bw.write(outLine);
			}

		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
		}

		return BookDao.data.size();
	}

	@Override
	public List<Book> doRetrieve(DTO dto) {
		//데이터를 찾아 return
		List<Book>  list=new ArrayList<Book>();
		
		//param
		Search  search= (Search) dto;
		
		int div = search.getSearchDiv();
		String searchWord = search.getSearchWord();
		for(Book book :BookDao.data) {
			//LOG.debug(book.getTitle().indexOf( search.getSearchWord()));
			//LOG.debug(book.getTitle().matches(".*"+search.getSearchWord()+".*"));
			//str.matches(".*"+검색어+".*")

			//.: 임의의 문자 [단 ‘'는 넣을 수 없습니다.]
			//*: 앞 문자가 0개 이상의 개수가 존재할 수 있습니다.			
			
//			if(search.getSearchDiv()==10 && book.getTitle().matches(".*"+search.getSearchWord()+".*")) {//제목으로 검색
//				list.add(book);
//			}
			
			
			switch(div) {
			
			case 10://제목
				if(book.getTitle().matches(".*"+searchWord+".*")) {//제목으로 검색
					list.add(book);
				}
				break;
				
			case 20://저자
				if(book.getAuthor().matches(".*"+searchWord+".*")) {//제목으로 검색
					list.add(book);
				}				
				break;	
				
			case 100://전체
				   list.add(book);
				break;					
			}
			
			
			
			
		}
		
		return list;
	}

	/**
	 * 도서목록에 도서존재 확인
	 * @param  book Book
	 * @return true(존재)/false(없음)
	 */
	public boolean isBookExists(Book book) {
		boolean flag = false;
		
		for(Book vo  :BookDao.data) {
			if(book.getIsbn().equals(vo.getIsbn())) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	@Override
	public int doSave(Book dto) {
		Book book =  dto;// param 읽기
		if( isBookExists(book) ==true) {
			LOG.debug("ISBN을 확인 하세요.("+book.getIsbn()+")");
			return 0;
		}
		
		boolean isContains = BookDao.data.contains(book);
		LOG.debug("isContains:"+isContains);
		
			
		
		boolean flag = BookDao.data.add(book);

		return (flag == true) ? 1 : 0;
	}

	@Override
	public int doUpdate(Book dto) {
		int flag = 0;
		// data delete, insert
		//1. 존재 여부 확인
		//2. 삭제
		//3. 등록
		if(isBookExists(dto)==true) {
			int tmp=0;
			tmp+=doDelete(dto);
			tmp+=doSave(dto);
			
			if(tmp==2)flag++;
		}
		
		
		return flag;
	}

	@Override
	public int doDelete(Book dto) {

		int flag = 0 ;
		
		for(int i=BookDao.data.size()-1;i>=0;i--) {
			Book tmp = BookDao.data.get(i);
			if(dto.getIsbn().equals(tmp.getIsbn())) {
				Book book =BookDao.data.remove(i);
				LOG.debug("삭제 데이터:"+book);
				flag = 1;
				break;
			}
		}
		
		return flag;
	}

	@Override
	public Book doSelectOne(Book obj) {
		// data.get(i)
		Book  book = null;
		//1. 존재 확인

		//2. 존재하면 Book을 retrun
		if(isBookExists(obj)) {
			for(Book tmpBook  :BookDao.data) {
				if( tmpBook.getIsbn().equals(obj.getIsbn())) {
					book = tmpBook;
					break;
				}
			}
		}		
		
		return book;
	}

}










