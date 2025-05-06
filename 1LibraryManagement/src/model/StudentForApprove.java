package model;

public class StudentForApprove {

	private long studentId;
	private String studentFirstName;
	private String studentLastName;
	private String studentPhoneNumber;
	private String studentUsername;
	private String studentPassword;


	public StudentForApprove(long studentId, String studentFirstName, String studentLastName, String studentPhoneNumber,
				String studentUsername, String studentPassword) {
			this.setStudentId(studentId);
			this.setStudentFirstName(studentFirstName);
			this.setStudentLastName(studentLastName);
			this.setStudentPhoneNumber(studentPhoneNumber);
			this.setStudentUsername(studentUsername);
			this.setStudentPassword(studentPassword);

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

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}


}
