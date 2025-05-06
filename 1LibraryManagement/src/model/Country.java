package model;

public class Country {

	private String countryCode;
	private String phoneCode;
	
	public Country(String countryCode, String phoneCode) {
		this.setCountryCode(countryCode);
		this.setPhoneCode(phoneCode);
	}
	
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	


}
