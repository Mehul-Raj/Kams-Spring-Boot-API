package com.aroha.kams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.kams.otpGenerator.RandomString;
import com.aroha.kams.payload.OtpPayload;
import com.aroha.kams.payload.UserPayload;

@Service
public class OtpService {

	@Autowired
	RandomString randString;

	@Autowired
	SendEmailService sendEmailService;

	public OtpPayload CreatePwd(OtpPayload otpPayload) {
		String pwd = randString.getAlphaNumericString();
		String email = otpPayload.geteMailForOtp();
		otpPayload.setOtpGenerated(pwd);
		boolean status = sendEmailService.sendOtp(email, pwd);
		if (status) {
			return otpPayload;
		} else {
			return new OtpPayload();
		}
	}

	public String CreatePwd(UserPayload userPayload) {
		String pwd = randString.getAlphaNumericString();
		return pwd;
	}

}
