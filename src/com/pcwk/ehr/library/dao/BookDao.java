package com.pcwk.ehr.library.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.LoggerManger;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.library.domain.Book;

public class BookDao implements WorkDiv, LoggerManger {
	//BookDao객체 생성시 파일에 있는 내용을 읽어 ArrayList<DTO>담는다.
	//종료시 data를 파일에 기록!
	
    public static ArrayList<Book>  data=new ArrayList<Book>();
    
    //저장파일 경로
    public final static String SAVE_FILE_PATH = "C:\\Users\\ITSC\\git\\BookMng\\boot.csv";
    
    
    public BookDao() {
    	readFile(SAVE_FILE_PATH);
    	LOG.debug("data.size():"+BookDao.data.size());
    	
    	for(Book book    :BookDao.data) {
    		LOG.debug( book.toString());
    	}
    }
    /**
     * 파일 읽기
     * @param path
     * @return
     */
    public int readFile(String filePath) {
    	int flag = 0;
    	
    	FileReader fr = null;
    	BufferedReader  br=null;
    	try {
    		fr=new FileReader(SAVE_FILE_PATH);
    		br=new BufferedReader(fr);
    		
    		
    		int data = 0;
    		String line = "";//데이터를 line단위로 관리
    		while(     ( line=br.readLine()) !=null ) {
    			LOG.debug(line);
    			//9791163031222,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문,이고잉 고경희,소프트웨어 공학,2019년 12월 06일,1
    			String inArray[] = line.split(",");
    			
    			//대여가능 여부
    			//1 -> true
    			//0 -> false
    			boolean avaliable = inArray[5].equals("1")?true:false;
    			
    			Book book=new Book(inArray[0], inArray[1], inArray[2], inArray[3], inArray[4], avaliable);
    			//ArrayList에 추가
    			BookDao.data.add(book);
    			
    		}
    		
    		if(BookDao.data.size()>0)flag++;
    		
    		
    	}catch(IOException e) {
    		LOG.debug("IOException:"+e.getMessage());
    	}finally {
    		if(null !=br) {
    			try {
					br.close();
				} catch (IOException e) {
					LOG.debug("IOException:"+e.getMessage());
				}
    		}
    	}
    	
    	
    	
    	
    	return flag;
    }
    
    /**
     * 파일 저장!
     * @param filePath
     * @return 
     */
    public int writeFile(String filePath) {
    	int flag = 0;
    	//ArrayList<Book> -> File로 기록
    	//
        //try -- with --resource
    	try(   	FileWriter  fw=new FileWriter(filePath);
    	    	BufferedWriter  bw=new BufferedWriter(fw);) {
    		
    		//9791163031222,Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문,이고잉 고경희,소프트웨어 공학,2019년 12월 06일,1
    		for(Book book  : BookDao.data) {
    			
    			String delim =",";
    			int avaliable = (book.isBorrow()==true)?1:0;
    			
    			String outLine = book.getIsbn()+delim+
		    					book.getTitle()+delim+
		    					book.getAuthor()+delim+
		    					book.getGenre()+delim+
		    					book.getPublicationDate()+delim+
		    					book.isBorrow()+"\n";	
    			bw.write(outLine);
    		}	
    		
    	}catch(IOException e) {
    		System.out.println("IOException:"+e.getMessage());
    	}
    	
    	return BookDao.data.size()>0?1:0;
    }
    

    
	@Override
	public List<DTO> doRetrieve(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doSave(DTO dto) {
		Book book=(Book) dto;//param 읽기
		
		boolean flag = BookDao.data.add(book);
		
		
		return (flag==true)?1:0;
	}

	@Override
	public int doUpdate(DTO dto) {
		//data delete, insert
		return 0;
	}

	@Override
	public int doDelete(DTO dto) {
		// data.remove(i)
		return 0;
	}

	@Override
	public DTO doSelectOne(DTO obj) {
		// data.get(i)
		return null;
	}



}
