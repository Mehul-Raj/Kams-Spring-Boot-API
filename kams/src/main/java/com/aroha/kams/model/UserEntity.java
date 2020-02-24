package com.aroha.kams.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_info")
public class UserEntity {

	
	
	private String userPwd;
	private String userName;
	private String userRole;
	private String userCompany;
	private String userdepartment;
	private String userTeamName;
	private String userProjectName;

	@Id
	private String eMail;

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getUserdepartment() {
		return userdepartment;
	}

	public void setUserdepartment(String userdepartment) {
		this.userdepartment = userdepartment;
	}

	public String getUserTeamName() {
		return userTeamName;
	}

	public void setUserTeamName(String userTeamName) {
		this.userTeamName = userTeamName;
	}

	public String getUserProjectName() {
		return userProjectName;
	}

	public void setUserProjectName(String userProjectName) {
		this.userProjectName = userProjectName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
