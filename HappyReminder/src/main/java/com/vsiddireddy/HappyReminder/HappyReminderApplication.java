package com.vsiddireddy.HappyReminder;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;

@SpringBootApplication
public class HappyReminderApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(HappyReminderApplication.class, args);
		StartScheduler();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "PUT", "POST", "DELETE").allowedOrigins("http://localhost:8080");
			}
		};
	}
	

	public static void StartScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		JobDetail sendEmails = newJob(EmailSender.class).withIdentity("send-email-job").build();
		
		//CronScheduleBuilder onceADay = CronScheduleBuilder.dailyAtHourAndMinute(0, 0);
		//SimpleTrigger trigger = newTrigger().withIdentity("trigger1").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
		// "0 0 12 1/1 * ? *"
		// 53 is min, 11 is hr
		Trigger trigger = newTrigger()
				.withIdentity("trigger1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 30 10 ? * *"))
				.forJob(sendEmails)
				.build();
		
		scheduler.scheduleJob(sendEmails, trigger);
	}
}