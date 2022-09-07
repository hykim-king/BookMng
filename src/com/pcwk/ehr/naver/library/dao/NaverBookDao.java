package com.pcwk.ehr.naver.library.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.LoggerManger;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.library.domain.Search;
import com.pcwk.ehr.naver.library.domain.Channel;
import com.pcwk.ehr.naver.library.domain.Item;

public class NaverBookDao implements WorkDiv<Item>, LoggerManger {

	private final String CLIENT_ID = "PF2hZcj7_cmVHc8unUOn";
	private final String CLIENT_SECRET = "Z2F9wQ0NVO";
	 
	private int cnt;
	private List<Item>  list = new ArrayList<Item>();
	public NaverBookDao() {
		cnt = 1;
	}


	public Channel searchtNaverBook(String searchWord,int startNum, int display) {
		String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + searchWord + "&display="+display+"+&start="+startNum;
			
		BufferedReader br = null;
		StringBuilder sb=new StringBuilder();		
		Channel channel = null;
		try {

			
			URL url = new URL(apiURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);

			int responseCode = con.getResponseCode();// 접속 상태 코드
			LOG.debug("responseCode:" + responseCode);

			if (responseCode == 200) {// 접속과, 조회 성공
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {// 접속 오류
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			LOG.debug("========================================================");
			LOG.debug("apiURL:" + apiURL);
			LOG.debug("도서 검색어:" + searchWord);
			LOG.debug("========================================================");
			
			String inputLine = "";// 데이터를 1줄씩 read
			while ((inputLine = br.readLine()) != null) {// 더이상 읽을 데이터가 없으면 null return
				sb.append(inputLine + "\n");
			}

			//JSON 파싱
			Gson gson=new Gson();
			
			channel = gson.fromJson(sb.toString(), Channel.class);
			LOG.debug("getTotal: "+channel.getTotal());
			LOG.debug("getStart: "+channel.getStart());
			LOG.debug("getDisplay:"+channel.getDisplay());
			
			
		} catch (IOException e) {

		}
		
		return channel;
	}

	@Override
	public List<Item> doRetrieve(DTO dto) {
		Search search = (Search)dto;
		
		//총글수
		//getStart
		//getDisplay
		int start = 0;
		int display = 10;
		int total   = 0;
		
		
		String text = "";
		try {
			text = URLEncoder.encode(search.getSearchWord(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//최초 10건 조회
		Channel channel=searchtNaverBook(text,1,display);
        
		if(null == channel)return list;
		
		for(Item item  :channel.getItems()) {
			list.add(item);
		}
		
		
		total = channel.getTotal();
		display = channel.getDisplay();
		
		boolean check = total > display && total !=0;
				
		while(total != start) {
			
			start= (start<total)?(cnt * display) + 1 : total;
			if(total > start) {
				channel = searchtNaverBook(text,start,display);
				cnt++;	
				for(Item item  :channel.getItems()) {
					list.add(item);
				}				
			}
					
		}
		System.out.println("list.size():"+list.size());
		return list;
	}

	@Override
	public int doSave(Item dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(Item dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Item dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Item doSelectOne(Item obj) {
		// TODO Auto-generated method stub
		return null;
	}

}