package com.aroha.kams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.aroha.kams.model.UserEntity;
import com.aroha.kams.payload.UserPayload;

@Service
public class SendEmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(UserEntity user) {
		boolean status = false;
		String toEmail = user.geteMail();
		String pwd = user.getUserPwd();
		String name = user.getUserName();
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject("User Id And Password For Aroha Cloud Service");
			msg.setText("Dear " + name + " ,\n" + "\n" + "The Password generated for your Account with email "
					+ toEmail + "  is : " + pwd
					+ "\n The Password has also been sent to your preferred mobile number registered with us."
					+ "To update your latest contact details, you can visit the 'My Account' section on www.aroha.co.in.com.\n"
					+ "\n" + "In case of any queries, kindly contact our customer service desk at the details below\n"
					+ "\n" + "\n" + "Warm Regards,\n" + "\n" + "ArohaTechnologies");
			javaMailSender.send(msg);
			status = true;
		} catch (Exception ex) {
			System.out.println(ex);
			status = false;
		}
		return status;

	}

	public boolean sendOtp(String emailId, String otp) {
		boolean status = false;
		String toEmail = emailId;
		String pwd = otp;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject("OTP For Aroha Cloud Service");
			msg.setText("Dear User ,\n" + "\n" + "The OTP generated for your Account with ID " + toEmail + "  is : "
					+ pwd + "\n The Password has also been sent to your preferred mobile number registered with us."
					+ "To update your latest contact details, you can visit the 'My Account' section on www.aroha.co.in.com.\n"
					+ "\n" + "In case of any queries, kindly contact our customer service desk at the details below\n"
					+ "\n" + "\n" + "Warm Regards,\n" + "\n" + "ArohaTechnologies");
			javaMailSender.send(msg);
			status = true;
		} catch (Exception ex) {
			System.out.println(ex);
			status = false;
		}
		return status;
	}

}
