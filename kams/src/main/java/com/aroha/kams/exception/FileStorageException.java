package com.aroha.kams.exception;

public class FileStorageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FileStorageException(String string) {
		this.msg = string;
	}
}