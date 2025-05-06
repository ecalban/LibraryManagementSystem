package model;

public class Librarian {

	private int librariantId;
	private String librarianFirstName;
	private String librarianLastName;
	private String librarianPhoneNumber;
	private String librarianUsername;
	private String librarianPassword;
	private String librarianStartDate;
	
	public Librarian(int librariantId, String librarianFirstName, String librarianLastName, String librarianPhoneNumber,
			String librarianUsername, String librarianPassword, String employmentStartDate) {
		this.setLibrariantId(librariantId);
		this.setLibrarianFirstName(librarianFirstName);
		this.setLibrarianLastName(librarianLastName);
		this.setLibrarianPhoneNumber(librarianPhoneNumber);
		this.setLibrarianUsername(librarianUsername);
		this.setLibrarianPassword(librarianPassword);
		this.setEmploymentStartDate(employmentStartDate);
	}
	
	public int getLibrariantId() {
		return librariantId;
	}
	public void setLibrariantId(int librariantId) {
		this.librariantId = librariantId;
	}
	public String getLibrarianFirstName() {
		return librarianFirstName;
	}
	public void setLibrarianFirstName(String librarianFirstName) {
		this.librarianFirstName = librarianFirstName;
	}
	public String getLibrarianLastName() {
		return librarianLastName;
	}
	public void setLibrarianLastName(String librarianLastName) {
		this.librarianLastName = librarianLastName;
	}
	public String getLibrarianPhoneNumber() {
		return librarianPhoneNumber;
	}
	public void setLibrarianPhoneNumber(String librarianPhoneNumber) {
		this.librarianPhoneNumber = librarianPhoneNumber;
	}
	public String getLibrarianUsername() {
		return librarianUsername;
	}
	public void setLibrarianUsername(String librarianUsername) {
		this.librarianUsername = librarianUsername;
	}
	public String getLibrarianPassword() {
		return librarianPassword;
	}
	public void setLibrarianPassword(String librarianPassword) {
		this.librarianPassword = librarianPassword;
	}
	public String getEmploymentStartDate() {
		return librarianStartDate;
	}
	public void setEmploymentStartDate(String librarianStartDate) {
		this.librarianStartDate = librarianStartDate;
	}
	
	
	
	
}
