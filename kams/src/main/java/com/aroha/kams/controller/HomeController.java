package com.aroha.kams.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.kams.model.UserEntity;
import com.aroha.kams.payload.OtpPayload;
import com.aroha.kams.payload.UserPayload;
import com.aroha.kams.repository.UserRepository;
import com.aroha.kams.service.HomeService;
import com.aroha.kams.service.OtpService;
import com.aroha.kams.service.SendEmailService;

@RestController
@RequestMapping("/api/dropbox")
@CrossOrigin("*")
public class HomeController {

	@Autowired
	HomeService homeService;

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	OtpService otpService;

	@Autowired
	UserRepository dao;
	
	@Autowired
	HttpSession session;

	@PostMapping("/checkLogin")
	public ResponseEntity<?> checkLogin(@RequestBody UserPayload userpayload) {
		Boolean isUserExists = homeService.findEmail(userpayload.geteMail());
		if (isUserExists) {
			UserPayload getUserpayload = homeService.checkLogin(userpayload);
			return ResponseEntity.ok(getUserpayload);
		} else {
			userpayload.setStatus("Error");
			userpayload.setMsg("User Not Found");
			return ResponseEntity.ok(userpayload);
		}
	}

	@GetMapping("destroy")
	public String logout() {
		return null;

	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody OtpPayload otpPayload) {
		OtpPayload msg = otpService.CreatePwd(otpPayload);
		return ResponseEntity.ok(msg);
	}

	@PostMapping("/validateOtp")
	public ResponseEntity<?> validiateOtp(@RequestBody OtpPayload otpPayload) {
		String otpGenerated = otpPayload.getOtpGenerated();
		String email = otpPayload.geteMailForOtp();
		if (otpGenerated.equals(otpPayload.getOtpEntered())) {
			//List<UserEntity> userList = 
			UserEntity user = dao.findByeMail(email);
			user.setUserPwd(otpPayload.getNewPassword());
			dao.save(user);
			return ResponseEntity.ok("Password Changed");
		} else {
			return ResponseEntity.ok("InCorrect Otp");
		}
	}

}