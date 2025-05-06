package model;

public class Admin {

	private int adminId;
	private String adminFirstName;
	private String adminLastName;
	private String adminPhoneNumber;
	private String adminUsername;
	private String adminPassword;
	
	public Admin(int adminId, String adminFirstName, String adminLastName, String adminPhoneNumber,
			String adminUsername, String adminPassword) {
		this.setAdminId(adminId);
		this.setAdminFirstName(adminFirstName);
		this.setAdminLastName(adminLastName);
		this.setAdminPhoneNumber(adminPhoneNumber);
		this.setAdminUsername(adminUsername);
		this.setAdminPassword(adminPassword);
	}
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminFirstName() {
		return adminFirstName;
	}
	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}
	public String getAdminLastName() {
		return adminLastName;
	}
	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}
	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}
	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
	
}
