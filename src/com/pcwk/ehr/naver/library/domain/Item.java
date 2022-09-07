package com.pcwk.ehr.naver.library.domain;

public class Item {
	private String title;		//제목
	private String link;		//링크
	private String image;		//이미
	private String author;		//저자
	private String discount;	//가격
	private String publisher;	//출판사
	private String pubdate;		//출판일
	private String isbn;		//ISBN
	
	public Item() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", link=" + link + ", image=" + image + ", author=" + author + ", discount="
				+ discount + ", publisher=" + publisher + ", pubdate=" + pubdate + ", isbn=" + isbn + "]";
	}
	
	
	
	
}
