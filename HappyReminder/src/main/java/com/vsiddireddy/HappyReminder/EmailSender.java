package com.vsiddireddy.HappyReminder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Configuration
public class EmailSender implements Job {
	
	private HomeController ds;
	private JavaMailSender mailSender;
	List<Map<String, Object>> list;
	UserRepository repo;
	
	public EmailSender() throws SchedulerException {
		mailSender = new JavaMailSenderImpl();
		((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
		((JavaMailSenderImpl) mailSender).setPort(587);
		((JavaMailSenderImpl) mailSender).setUsername("happyreminderapp@gmail.com");
		((JavaMailSenderImpl) mailSender).setPassword("tetaqyxsyuidnpah");
		
		ds = new HomeController();
		repo = new UserRepository(ds.dataSource());
		list = repo.getAllRemindersFullData();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("EMAIL IS GOING TO BE SENT IF ANY DATES MATCH!!!!");
		System.out.println(list.toString());
		for (int x = 0; x < list.size(); x++) {
			String[] reminderDate = list.get(x).get("reminderDate").toString().split("-");
			int reminderDateMonthInt = Integer.parseInt(reminderDate[1]);
			int reminderDateDayInt = Integer.parseInt(reminderDate[2]);
			System.out.println(list.get(x).get("reminderDate"));
			System.out.println(reminderDate.toString());
			LocalDate date = LocalDate.now();
			if (date.getMonthValue() == reminderDateMonthInt && date.getDayOfMonth() == reminderDateDayInt) {
				System.out.println("THE EMAIL CONDITIONS ARE MET. SENDING EMAIL (EMAIL SENDING IN PROGRESS)");
				String toEmail = list.get(x).get("userEmail").toString();
				String name = list.get(x).get("name").toString();
				String birthDate = list.get(x).get("birthDate").toString();
				System.out.println(toEmail);
				sendEmail(toEmail, name, birthDate);
			}
		}
	}

	private void sendEmail(String toEmail, String name, String birthDate) {
		//provide recipient's email ID
		String to = toEmail;
		//provide sender's email ID
		String from = "happyreminderapp@gmail.com";
		//provide Mailtrap's username
		final String username = "happyreminderapp";
		//provide Mailtrap's password
		final String password = "tetaqyxsyuidnpah";
		//provide Mailtrap's host address
		String host = "smtp.gmail.com";
	    //configure Mailtrap's SMTP server details
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", "587");
	    //create the Session object
	    Session session = Session.getInstance(props,
	    	new jakarta.mail.Authenticator() {
	    		protected PasswordAuthentication getPasswordAuthentication() {
	    			return new PasswordAuthentication(username, password);
	    		}
	    	}
	    );
	    
	    try {
		    //create a MimeMessage object
		    Message message = new MimeMessage(session);
		    //set From email field
		    message.setFrom(new InternetAddress(from));
		    //set To email field
		    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    //set email subject field
		    message.setSubject(name + "'s birthday is coming up soon!");
		    //set the content of the email message
		    message.setText(name + " has a birthday on " + birthDate + ". This is your annual reminder.");
		    //send the email message
		    Transport.send(message);
		    System.out.println("Email Message Sent Successfully");
	    } catch (MessagingException e) {
	    	throw new RuntimeException(e);
	    }
	}
}
