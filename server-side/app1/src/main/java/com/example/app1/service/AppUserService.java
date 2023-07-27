package com.example.app1.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app1.dto.ChangePasswordStatus;
import com.example.app1.dto.EmailVerificationStatus;
import com.example.app1.dto.LoginSuccessStatus;
import com.example.app1.dto.ResetPasswordStatus;
import com.example.app1.dto.SendMailOnForgottenPasswordStatus;
import com.example.app1.dto.SignupSuccessStatus;
import com.example.app1.entity.AppUser;
import com.example.app1.repository.AppUserRepository;

@Service
public class AppUserService {
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private MailService mailService;
	private ResetPasswordStatus resetPassword;
	
	public SignupSuccessStatus save(AppUser appUser) throws Exception
	{
		String token = generateToken();
		appUser.setToken(token);
		appUserRepository.save(appUser);
		mailService.sendMailToVerifyEmailId(token, appUser.getEmail());
		SignupSuccessStatus signupSuccessStatus = new SignupSuccessStatus();
		signupSuccessStatus.setMessage("Signup is very success. We have sent a mail. Pls verify your email by clicking on activating email from your email box.");
		signupSuccessStatus.setStatus(true);
		return signupSuccessStatus;
	}
	private String generateToken()
	{
		String str =  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String token =  "";
		for(int i = 1; i <= 5; i++)
		{
			token += str.charAt((int)(str.length() * Math.random()));
		}
		return token;
	}
	public LoginSuccessStatus login(AppUser clientObject)
	{
		LoginSuccessStatus loginSuccessStatus = new LoginSuccessStatus();
		loginSuccessStatus.setMessage("User is not available");
		Optional<AppUser> optional = 
					appUserRepository.findById(clientObject.getEmail());
		if(optional.isPresent())
		{
			AppUser dbObject = optional.get();
			loginSuccessStatus.setMessage("Invalid Password");
			if(dbObject.getPassword().equals(clientObject.getPassword()))
			{
				loginSuccessStatus.setMessage("Email ID is not verified. "
						+ "We have  sent a mail to mail box. "
						+ "Pls verify your email id from your mail box");
				if(dbObject.getStatus() == 1)
				{
					loginSuccessStatus.setMessage("Login Success");
					loginSuccessStatus.setStatus(true);
				}
			}
		}
		return loginSuccessStatus;
	}

	public EmailVerificationStatus verifyMailId(String token, String email)
	{
		EmailVerificationStatus emailVerificationStatus = new EmailVerificationStatus();
		emailVerificationStatus.setMessage("Sorry. the token was wrong");
		AppUser appUser = appUserRepository.findByTokenAndEmail(token, email);
		
		if(appUser !=   null)
		{
			appUser.setStatus(1);
			appUserRepository.save(appUser);
			emailVerificationStatus.setStatus(true);
			emailVerificationStatus.setMessage("Your email verified successfully");
		}
		return emailVerificationStatus;

	
	}
	
	public ResetPasswordStatus
	resetPassword(String email,
				String password,
				String confirmPassword)
	{
		ResetPasswordStatus resetPasswordStatus = new ResetPasswordStatus();
		resetPasswordStatus.setMessage("Password and confirm password " + "both are not same");
		if(password.equals(confirmPassword))
		{
			AppUser appUser = appUserRepository.findById(email).get();
			appUser.setPassword(password);
			appUserRepository.save(appUser);
			resetPasswordStatus.setMessage("password change successfully");
			resetPasswordStatus.setStatus(true);
		}
		return resetPasswordStatus;
	}
	
	public ChangePasswordStatus changePassword(
												String email,
												String oldpassword,
												String password,
												String confirmPassword)
	{
		ChangePasswordStatus changePasswordStatus = new ChangePasswordStatus();
		changePasswordStatus.setMessage("Old password is not correct");
		AppUser appUser = appUserRepository.findById(email).get();
		if(appUser.getPassword().equals(oldpassword))
		{
			changePasswordStatus.setMessage("Password and confirm password " + "both are not same");
		if(password.equals(confirmPassword))
		{
			appUser.setPassword(password);
			appUserRepository.save(appUser);
			changePasswordStatus.setMessage("password change successfully");
			changePasswordStatus.setStatus(true);
		}
	}
		return changePasswordStatus;
}	
	public SendMailOnForgottenPasswordStatus sendMailOnForgottenPassword(AppUser appUser)throws Exception
	{
		Optional<AppUser> optional =
				appUserRepository.findById(appUser.getEmail());
		SendMailOnForgottenPasswordStatus forgottenPasswordStatus = 
				new SendMailOnForgottenPasswordStatus();
		forgottenPasswordStatus.setMessage("This user is not available");
		if(optional.isPresent())
		{
			mailService.sendMailOnForgottenPassword(appUser.getEmail());
			forgottenPasswordStatus.setMessage("Mail sent to " + appUser.getEmail() + "with a password reset form");
			forgottenPasswordStatus.setStatus(true);
		}
		return forgottenPasswordStatus;
	}
}



/*
SELECT * FROM APP_USER ;
 INSERT INTO APP_USER(STATUS,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,TOKEN)VALUES(1,'a@a.com','abc','xyz','123','test');
 
 {
  "email" : "a@a.com",
  "oldPassword":"123",
  "password":"1122",
  "confirmPassword":"1122",
 }
  
 * */



//it shud have service mandotry, its intercing with repositry


