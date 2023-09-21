package com.vsiddireddy.HappyReminder;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSender mailSender = new JavaMailSenderImpl();
	    ((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
	    ((JavaMailSenderImpl) mailSender).setPort(25);

	    ((JavaMailSenderImpl) mailSender).setUsername("happyreminderapp@gmail.com");
	    ((JavaMailSenderImpl) mailSender).setPassword("tetaqyxsyuidnpah");

	    Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return mailSender;
	}
	
	@Bean
	public SimpleMailMessage emailTemplate()
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("vsiddireddy@gmail.com");
		message.setFrom("happyreminderapp@gmail.com");
		message.setSubject("HELLO THERE!");
	    message.setText("FATAL - Application crash. Save your job !!");
	    return message;
	}
}
