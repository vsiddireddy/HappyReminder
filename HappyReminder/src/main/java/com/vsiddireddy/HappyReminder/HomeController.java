package com.vsiddireddy.HappyReminder;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class HomeController {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/HappyReminderDB");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("siddi7188");
        return dataSourceBuilder.build();
    }
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	
	@GetMapping("/secured")
	@ResponseBody
	public String secured() {
		return "user.html";
	}
	
	@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView getUserInfo(Authentication authentication, @RequestParam(required = false) String personName, @RequestParam(required = false) Date birthDate, @RequestParam(required = false) String relation, @RequestParam(required = false) Date reminderDate, @RequestParam(required=false) String timezone) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Access the user's information
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;

                // Access user attributes
                String email = oAuth2User.getAttribute("email");
                String name = oAuth2User.getAttribute("name");
                
                User user = new User(email, name);
                UserRepository repo = new UserRepository(dataSource());
                
                boolean doesExist = repo.userExists(email);
                System.out.println("doesExist: " + doesExist);
                
                if (!doesExist) {
                	repo.addNewUser(user);
                }
                ModelAndView mav = new ModelAndView("user-page"); // must be name of html file
                
                if (personName != null && !personName.isEmpty()) {
                	System.out.println(email);
                	System.out.println("personName: " + personName);
                	System.out.println("birthDate: " + birthDate);
                	mav.addObject("userEmailID", email);
                	mav.addObject("personName", personName);
                	mav.addObject("birthDate", birthDate);
                	mav.addObject("relation", relation);
                	mav.addObject("reminderDate", reminderDate);
                	mav.addObject("timezone", timezone);
                	repo.addNewReminderPerson(email, personName, birthDate, relation, reminderDate, timezone);
                	
                	List<Map<String, Object>> obj = repo.getAllReminders(email);
                	System.out.println(obj.toString());
                	mav.addObject("obj", obj.toString());
                }
                mav.addObject("info", "Welcome, " + name + " (" + email + ")!");
                
                return mav;
            }
        }
        return null;
    }
	
}
