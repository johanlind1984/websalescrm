package com.johanlind.websalescrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class WebsalescrmApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(WebsalescrmApplication.class, args);
	}

}
