package model;

public class Student {
	
	private long studentId;
	private String studentFirstName;
	private String studentLastName;
	private String studentPhoneNumber;
	private String studentEmail;
	private String studentDepartment;
	private String studentMembershipDate;
	private String[] studentBorrowedBooks;
	private String[] studentReturnDate;
	private int studentLateFee;
	
	
	public Student(long studentId, String studentFirstName, String studentLastName, String studentPhoneNumber,
			String studentEmail, String studentDepartment, String studentMembershipDate, String[] studentBorrowedBooks,
			String[] studentReturnDate, int studentLateFee) {
		this.setStudentId(studentId);
		this.setStudentFirstName(studentFirstName);
		this.setStudentLastName(studentLastName);
		this.setStudentPhoneNumber(studentPhoneNumber);
		this.setStudentEmail(studentEmail);
		this.setStudentDepartment(studentDepartment);
		this.setStudentMembershipDate(studentMembershipDate);
		this.setStudentBorrowedBooks(studentBorrowedBooks);
		this.setStudentReturnDate(studentReturnDate);
		this.setStudentLateFee(studentLateFee);

	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getStudentPhoneNumber() {
		return studentPhoneNumber;
	}
	public void setStudentPhoneNumber(String studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentDepartment() {
		return studentDepartment;
	}
	public void setStudentDepartment(String studentDepartment) {
		this.studentDepartment = studentDepartment;
	}
	public String getStudentMembershipDate() {
		return studentMembershipDate;
	}
	public void setStudentMembershipDate(String studentMembershipDate) {
		this.studentMembershipDate = studentMembershipDate;
	}
	public String[] getStudentBorrowedBooks() {
		return studentBorrowedBooks;
	}
	public void setStudentBorrowedBooks(String[] studentBorrowedBooks) {
		this.studentBorrowedBooks = studentBorrowedBooks;
	}
	public String[] getStudentReturnDate() {
		return studentReturnDate;
	}
	public void setStudentReturnDate(String[] studentReturnDate) {
		this.studentReturnDate = studentReturnDate;
	}
	public int getStudentLateFee() {
		return studentLateFee;
	}
	public void setStudentLateFee(int studentLateFee) {
		this.studentLateFee = studentLateFee;
	}
	
}


