package com.frnmz.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="userinfo")
public class UserInfo {
	@Id
	private String emailId;
	private String fullName;
	private String password;
	private String mobileNumber;
	private boolean enabled;
	private boolean adminAccess;

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean getAdminAccess() {
		return adminAccess;
	}
	public void setAdminAccess(boolean adminAccess) {
		this.adminAccess = adminAccess;
	}
	
	@Override
	public String toString() {
		return "UserInfo [emailId=" + emailId + ", fullName=" + fullName
				+ ", password=" + password + ", mobileNumber=" + mobileNumber
				+ ", enabled=" + enabled + ", adminAccess=" + adminAccess + "]";
	}
	
	@Override
	public boolean equals(Object uiObj){
		if(uiObj != null && uiObj instanceof UserInfo){
			UserInfo userInfo = (UserInfo) uiObj;
			return emailId.equals(userInfo.getEmailId());
		}
		
		return false;
	}
	
	public UserInfo(){}
	
	public UserInfo(String email){
		emailId = email;
	}
	
	@Override
	public int hashCode(){
		return emailId.hashCode();
	}
}