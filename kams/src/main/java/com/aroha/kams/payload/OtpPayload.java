package com.aroha.kams.payload;

public class OtpPayload {
	String otpGenerated;
	String eMailForOtp;
	String otpEntered;
	String newPassword;

	public String getOtpGenerated() {
		return otpGenerated;
	}

	public void setOtpGenerated(String otpGenerated) {
		this.otpGenerated = otpGenerated;
	}

	public String geteMailForOtp() {
		return eMailForOtp;
	}

	public void seteMailForOtp(String eMailForOtp) {
		this.eMailForOtp = eMailForOtp;
	}

	public String getOtpEntered() {
		return otpEntered;
	}

	public void setOtpEntered(String otpEntered) {
		this.otpEntered = otpEntered;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
