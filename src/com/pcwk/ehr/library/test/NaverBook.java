package com.pcwk.ehr.library.test;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.library.dao.BookDao;
import com.pcwk.ehr.library.domain.Book;
import com.pcwk.ehr.library.domain.Search;
import com.pcwk.ehr.naver.library.dao.NaverBookDao;
import com.pcwk.ehr.naver.library.domain.Item;

public class NaverBook {

	NaverBookDao dao;//BookDao (DAO: Data Access Object)
	Book  book01;
	Book  book02;
	Book  book03;
	Search search;
	public NaverBook() {
		dao=new NaverBookDao();
		book01=new Book("9791163031222_001","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_001","이고잉 고경희_001","소프트웨어 공학","2019-12-06",true);
		book02=new Book("9791163031222_002","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_002","이고잉 고경희_002","소프트웨어 공학","2019-12-06",true);
		book03=new Book("9791163031222_003","Do it! 지옥에서 온 문서 관리자 깃&깃허브 입문_003","이고잉 고경희_003","소프트웨어 공학","2019-12-06",false);
		search = new Search();
	}	
	
	public void doRetrieve() {
		search.setSearchWord("오라클");
		dao.doRetrieve(search);
	}
	
	public static void main(String[] args) {
		NaverBook main=new NaverBook();
		main.doRetrieve();
	}

}
