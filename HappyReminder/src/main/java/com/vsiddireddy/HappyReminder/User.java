package com.vsiddireddy.HappyReminder;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	String emailID; // Primary Key
	
	String name;
	
	public User(String emailID, String name) {
		this.emailID = emailID;
		this.name = name;
	}
	
	public String getEmailId() {
		return emailID;
	}
	public String getName() {
		return name;
	}
}
