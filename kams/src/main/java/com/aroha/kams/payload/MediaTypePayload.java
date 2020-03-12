package com.aroha.kams.payload;

import java.math.BigInteger;

public class MediaTypePayload {
	
	private String typeName;
	private BigInteger typeNumber;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public BigInteger getTypeNumber() {
		return typeNumber;
	}
	public void setTypeNumber(BigInteger typeNumber) {
		this.typeNumber = typeNumber;
	}	
}
