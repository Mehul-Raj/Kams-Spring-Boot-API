package com.aroha.kams.payload;

import java.math.BigInteger;

public class TagTypePayload {

	private String tagName;
	private BigInteger tagNumber;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public BigInteger getTagNumber() {
		return tagNumber;
	}
	public void setTanNumber(BigInteger tagNumber) {
		this.tagNumber = tagNumber;
	}
	
	
}
