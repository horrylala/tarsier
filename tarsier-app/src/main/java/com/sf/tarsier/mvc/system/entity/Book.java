package com.sf.tarsier.mvc.system.entity;

public class Book {
	private Integer id;
	private String bookName;
	private String author;
	private Integer price;

	public Book(String bookName, String author, Integer price) {
		super();
		this.bookName = bookName;
		this.author = author;
		this.price = price;
	}

	public Book() {
		super();
	}

	public Book(Integer id, String bookName, String author, Integer price) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
