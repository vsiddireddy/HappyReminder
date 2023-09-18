package com.vsiddireddy.HappyReminder;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository {
	
	private JdbcTemplate jdbcTemplate;
	
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void addNewUser(User user) {
    	String query = "INSERT INTO User (id, name) VALUES (?, ?)";
    	jdbcTemplate.update(query, user.emailID, user.getName());
    	
    }
    
    public String getName(String emailID) {
    	String query = "SELECT name FROM User WHERE " + emailID + "=User.id"; // TODO
    	return this.jdbcTemplate.queryForObject(query, String.class);
    }
    
    public int getCount() {
    	String query = "SELECT count(*) FROM User";
    	return this.jdbcTemplate.queryForObject(query, Integer.class);
    }
    
    public boolean userExists(String email) {
    	String query = "SELECT count(id) FROM User WHERE id=?";
    	int count = this.jdbcTemplate.queryForObject(query, Integer.class, email);
    	if (count != 0) {
    		return true;
    	}
    	return false;
    }
    
    
    public void addNewReminderPerson(String email, String name, Date birthDate, String relation, Date reminderDate, String timezone) { // TODO determine which date to use, currently using sql
    	String query = "INSERT INTO ReminderInfo (id, userEmail, name, relation, birthDate, reminderDate, timezone) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	this.jdbcTemplate.update(query, UUID.randomUUID().toString(), email, name, relation, birthDate, reminderDate, timezone);
    }
    
    public void deleteReminderPerson(UUID uuid) {
    	String query = "DELETE FROM ReminderInfo WHERE id=?";
    	this.jdbcTemplate.update(query, uuid);
    }
    
    public List<Map<String, Object>> getAllReminders(String email) {
    	String query = "SELECT name,relation,birthDate,reminderDate,timezone FROM ReminderInfo WHERE userEmail=?";

    	/*
    	String query1 = "SELECT name FROM ReminderInfo WHERE userEmail=?";
    	String query2 = "SELECT birthDate FROM ReminderInfo WHERE userEmail=?";
    	String query3 = "SELECT relation FROM ReminderInfo WHERE userEmail=?";
    	String query4 = "SELECT reminderDate FROM ReminderInfo WHERE userEmail=?";
    	String name =  this.jdbcTemplate.queryForObject(query1, String.class, email);
    	Date birthDate =  this.jdbcTemplate.queryForObject(query2, Date.class, email);
    	String relation =  this.jdbcTemplate.queryForObject(query3, String.class, email);
    	Date reminderDate =  this.jdbcTemplate.queryForObject(query4, Date.class, email);
    	String[] arr = {name, birthDate.toString(), relation, reminderDate.toString()};
    	*/
    	
    	List<Map<String, Object>> names = this.jdbcTemplate.queryForList(query, email);
    	
    	System.out.println("NAMES:   ");
    	System.out.println(names.toString());
    	System.out.println(names.getClass());
    	//System.out.println(names.get(0).toString());
    	
    	return names;
    }
    
    public List<Map<String, Object>> getAllReminders() {
    	return this.jdbcTemplate.queryForList("SELECT name,relation,birthDate,reminderDate,timezone FROM ReminderInfo");
    }
    
}
