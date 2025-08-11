package model;

public class StudentForApprove {

	private long studentId;
	private String studentFirstName;
	private String studentLastName;
	private String studentPhoneNumber;
	private String studentEmail;
	private String studentDepartment;


	public StudentForApprove(long studentId, String studentFirstName, String studentLastName, String studentPhoneNumber,
				String studentEmail, String studentDepartment) {
			this.setStudentId(studentId);
			this.setStudentFirstName(studentFirstName);
			this.setStudentLastName(studentLastName);
			this.setStudentPhoneNumber(studentPhoneNumber);
			this.setStudentEmail(studentEmail);
			this.setStudentDepartment(studentDepartment);

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


}
