package com.pcwk.ehr.naver.library.domain;

import java.util.ArrayList;
import java.util.List;



public class Channel {
	List<Item> items=new ArrayList<Item>();
	
	private int total;//총글수
	private int start;//시작번호
	private int display;//시작번호
	
	
	public Channel() {
		
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return "Channel [items=" + items + ", total=" + total + ", start=" + start + ", display=" + display + "]";
	}
	
	
}

