package com.pcwk.ehr.library.test;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.LoggerManger;
import com.pcwk.ehr.library.dao.BookDao;
import com.pcwk.ehr.library.domain.Book;

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
	}
	
	public static void main(String[] args) {
		BookDaoTest main=new BookDaoTest();
		main.doSave();
		
		
	}

}
