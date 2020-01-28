package com.aroha.kams.payload;

public class UserPayload {
	private String userPwd;
	private String userName;
	private String userRole;
	private String userCompany;
	private String userdepartment;
	private String userTeamName;
	private String userProjectName;
	private String eMail;
	private String msg;
	private String status;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
}
