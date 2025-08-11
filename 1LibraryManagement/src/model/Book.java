package model;

public class Book {

	private int bookId;
	private String bookTitle;
	private String bookAuthor;
	private String bookDescription;
	private String bookCategory;
	private String bookStatus;

	public Book(int bookId, String bookTitle, String bookAuthor, String bookDescription, String bookCategory,
			String bookStatus) {
		this.setBookId(bookId);
		this.setBookTitle(bookTitle);
		this.setBookAuthor(bookAuthor);
		this.setBookDescription(bookDescription);
		this.setBookCategory(bookCategory); 
		this.setBookStatus(bookStatus);
	}


	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}


	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}


