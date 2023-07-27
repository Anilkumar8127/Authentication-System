package com.example.app1.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app1.entity.AppUser;
import com.example.app1.repository.AppUserRepository;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String sendMailToVerifyEmailId(String token, String email) throws Exception
	{
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true);
		helper.setTo(email);
		helper.setSubject("Subject: Verify your Email");
		StringBuffer sb = new StringBuffer();
		sb.append("<a href='http://localhost:9090/admin/verifyMailId/");
		sb.append(token + "/" + email + "'> Verify</a>");
		helper.setText(sb.toString(), true);
		javaMailSender.send(mime);
		return " mail Sent successfully";
	}
	public String sendMailOnForgottenPassword(String email) throws Exception
	{
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true);
		helper.setTo(email);  
		helper.setSubject("Subject: Reset Your Password");
		StringBuffer sb = new StringBuffer();
		sb.append("<form action='http://localhost:9090/admin/resetPassword' method='post'>");
		sb.append("<input type='hidden' name='email' value='" + email + "'>");
		sb.append("New Password:<input type='password' name='password'><br>");
		sb.append("Confirm Password:<input type='password' name='confirmPassword'><br>");
		sb.append("<input type='submit' value='Reset Password'>");
		sb.append("</form>");
		helper.setText(sb.toString(), true);
		javaMailSender.send(mime);
		return " mail Sent successfully";
	}	
}