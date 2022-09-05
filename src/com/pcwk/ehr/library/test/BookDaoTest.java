package com.pcwk.ehr.library.test;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.LoggerManger;
import com.pcwk.ehr.library.dao.BookDao;
import com.pcwk.ehr.library.domain.Book;
import com.pcwk.ehr.library.domain.Search;

public class BookDaoTest implements LoggerManger {

	BookDao dao;//BookDao (DAO: Data Access Object)
	Book  book01;
	Book  book02;
	Book  book03;
	
	public BookDaoTest() {
		dao=new BookDao();
		book01=new Book("9791163031222_001","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_001","이고잉 고경희_001","소프트웨어 공학","2019-12-06",true);
		book02=new Book("9791163031222_002","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_002","이고잉 고경희_002","소프트웨어 공학","2019-12-06",true);
		book03=new Book("9791163031222_003","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_003","이고잉 고경희_003","소프트웨어 공학","2019-12-06",false);
		
	}
	//저장
	public void doSave() {
		int flag = dao.doSave(book01);
		if(flag ==1) {
			LOG.debug(book01);
			LOG.debug("등록 성공");
		}else {
			LOG.debug("등록 실패");
		}
		
		for(Book book  :BookDao.data) {
			System.out.println("book:"+book);
		}
		
		dao.writeFile(BookDao.SAVE_FILE_PATH);
		//dao.writeFileJson(BookDao.SAVE_JSON_FILE_PATH);
	}
	
	public void doDelete() {
		int flag = dao.doDelete(book02);
		if(flag ==1) {
			LOG.debug(book02);
			LOG.debug("삭제 성공");			
		}else {
			LOG.debug("삭제 실패");		
		}
		
		for(Book book  :BookDao.data) {
			System.out.println("book:"+book);
		}
		
		dao.writeFile(BookDao.SAVE_FILE_PATH);
	}
	
	public void dispArrayList() {
		for(Book book  :BookDao.data) {
			LOG.debug(book);
		}		
	}
	
	public void doUpdate() {
		
		book01.setTitle(book01.getTitle()+"_U");
		book01.setAuthor(book01.getAuthor()+"_U");
		book01.setPublicationDate(book01.getPublicationDate()+"_U");
		book01.setGenre(book01.getGenre()+"_U");
		int flag = dao.doUpdate(book01);
		if(1==flag) {
			LOG.debug(book01);
			LOG.debug("수정 성공");					
		}else {
			LOG.debug("수정 실패");			
		}
		
		dispArrayList();
		dao.writeFile(BookDao.SAVE_FILE_PATH);
	}
	
	public void deleteAll() {
		dao.doDelete(book01);
		dao.doDelete(book02);
		dao.doDelete(book03);
	}
	
	public void doSelectOne() {
		
		book01.setIsbn("9791162242261");
		if(null !=dao.doSelectOne(book01)) {
		    LOG.debug(dao.doSelectOne(book01));
			LOG.debug("조회 성공");		
		}else {
			LOG.debug("조회 실패");
		}
	}
	
	public void doRetrieve() {
		Search search=new Search(20, "김석훈");
		List<Book> list = dao.doRetrieve(search);
		
		for(Book book :list) {
			LOG.debug("book:"+book);
		}
		
	}
	
	public Book getInputData(Scanner scanner) {
		Book book = null;
		String[] dataArr = scanner.nextLine().trim().split(",");
		// 대여가능 여부
		// 1 -> true
		// 0 -> false		
		boolean avaliable = dataArr[5].equals("1") ? true : false;
		book = new Book(dataArr[0], dataArr[1], dataArr[2], dataArr[3], dataArr[4], avaliable);
		
		return book;
	}
	
	public static void main(String[] args) {
		BookDaoTest main=new BookDaoTest();
		//삭제
		//main.deleteAll();
		
		//등록
		//main.doSave();
		
		//삭제
		//main.doDelete();
		
		//수정
		//main.doUpdate();
		
		//단건 조회
		//main.doSelectOne();
		//main.doRetrieve();
		
		
		//--------------------------------------------------------------------//
		//-Keyboard 입력                                                                                                              //
		//--------------------------------------------------------------------//		
		
		Scanner scanner=new Scanner(System.in);
		String  inCommand = "";//명령어
		do {
			System.out.println("===Menu===");
			System.out.println("C : 등록    ");
			System.out.println("U : 수정    ");
			System.out.println("D : 삭제    ");
			System.out.println("OS: 단건조회  ");
			System.out.println("AS: 목록조회  ");
			System.out.println("Q : 종료    ");
			System.out.print(">>");
			
			//scanner에서 데이터 입력
			inCommand = scanner.nextLine();
			inCommand = inCommand.trim();//양쪽 공백, 대문자로 변환
			
			Book book = null;//
			String[] dataArr = null;
			String readData  = "";//command 입력 데이터
			
			switch(inCommand.toUpperCase()) {
			
			case "AS"://목록조회
				System.out.print("목록조회: 제목,검색어>>");
				String[] searchArr = scanner.nextLine().trim().split(",");
				int searchDiv = 100;
				
				//검색구분
				if(searchArr[0].equals("제목")) {
					searchDiv = 10;
				}else if(searchArr[0].equals("저자")) {
					searchDiv = 20;
				}
				
				String searchWord = searchArr[1];
				
				Search search=new Search(searchDiv, searchWord);
				List<Book> searchList = main.dao.doRetrieve(search);
				System.out.println("-----------------------------------------");
				System.out.println("도서번호\t\t\t제목\t\t\t저자\t\t\t장르\t\t\t출판일 \t\t\t대출가능 여부");
				System.out.println("-----------------------------------------");
				String delim = "\t";
				for(Book tmp :searchList) {
					String outLine = tmp.getIsbn() + delim + tmp.getTitle() + delim + tmp.getAuthor() + delim
							+ tmp.getGenre() + delim + tmp.getPublicationDate() + delim + tmp.isBorrow() + "\n";
					System.out.println(outLine);
				}
				
				break;
				
				
			case "U"://수정
				System.out.println("수정: 9791163031222_003,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_002_U,이고잉 고경희_002_U,소프트웨어 공학_U,2019-12-06_U,0>>");
				
				
				Book saveBook = main.getInputData(scanner);
				int upFlag = main.dao.doUpdate(saveBook);
				if(1==upFlag) {
					LOG.debug("수정 되었습니다.!.");
				}else {
					LOG.debug("수정 실패!");
				}
				
				break;
				
			case "C"://등록
				//
				System.out.println("등록: 9791163031222_003,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_003,이고잉 고경희_003,소프트웨어 공학,2019-12-06,0>>");

				book = main.getInputData(scanner);
				int saveFlag = main.dao.doSave(book);
				if(saveFlag==1) {
					LOG.debug(book.getTitle()+" 등록 되었습니다.");
				}else {
					LOG.debug(book.getTitle()+" 등록 실패!.");
				}
				
				break;
				
			case "OS"://단건조회
				System.out.print("단건조회:9791162242261>>");
				readData = scanner.nextLine().trim();
				book=new Book();
				book.setIsbn(readData);
				
				Book selectOne = main.dao.doSelectOne(book);
				if(null != selectOne) {
					LOG.debug(selectOne+" 조회 되었습니다.!.");
				}else {
					LOG.debug(selectOne+" 조회 실패.!.");
				}
				break;
			
			case "D"://삭제
				System.out.print("삭제:9791163031222_001>>");
				readData = scanner.nextLine().trim();
				book=new Book();
				book.setIsbn(readData);
				int delFlag = main.dao.doDelete(book);
				if(1==delFlag) {
					LOG.debug(book.getIsbn()+" 삭제 되었습니다.!.");
				}else {
					LOG.debug(book.getIsbn()+" 삭제 실패!.");
				}
				
				break;
			

			
			case "Q"://data파일에 저장하고, 종료
				int flag = main.dao.writeFile(BookDao.SAVE_FILE_PATH);
				LOG.debug("저장건수:"+flag);
				break;
			}//--switch
			
		}while(!inCommand.equalsIgnoreCase("Q"));
		
		LOG.debug("==========================================================");
		LOG.debug("=프로그램 종료===============================================");
		LOG.debug("==========================================================");		
	}

}















