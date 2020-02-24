package com.aroha.kams.payload;

public class SearchByPayload {
	private String eMail;
	private String searchByTag;
	private String searchByType;

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getSearchByTag() {
		return searchByTag;
	}

	public void setSearchByTag(String searchByTag) {
		this.searchByTag = searchByTag;
	}

	public String getSearchByType() {
		return searchByType;
	}

	public void setSearchByType(String searchByType) {
		this.searchByType = searchByType;
	}

}
