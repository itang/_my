package com.packtpub.felix.bookshelf.inventory.impl.mock;

import com.packtpub.felix.bookshelf.inventory.api.MutableBook;

public class MutableBookImpl implements MutableBook {
	private String isbn;
	private String author;
	private String title;
	private String category;
	private int rating;

	// private Date startDate;
	// private Date finishDate;
	// private URL frontCover;

	public MutableBookImpl(String isbn) {
		setIsbn(isbn);
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;

	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return this.rating;
	}

	@Override
	public String toString() {
		return "MutableBookImpl [isbn=" + isbn + ", author=" + author
				+ ", title=" + title + ", category=" + category + ", rating="
				+ rating + "]";
	}

	// public Date getStartDate(){
	// return this.startDate;
	// }
	// public Date getFinishDate(){
	// return this.finishDate;
	// }
	// public URL getFrontCover(){
	// return this.frontCover;
	// }
	//
	// public void setStartDate(Date startDate){
	// this.startDate = startDate;
	// }
	// public void setFinishDate(Date finishDate){
	// this.finishDate = finishDate;
	// }
	// public void setFrontCover(URL frontCover){
	// this.frontCover = frontConver;
	// }
	
	

}