package com.aroha.kams;

import java.util.TimeZone;


import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aroha.kams.properties.FileStorageProperties;


@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class KamsApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {

		}
	}
	
	@Configuration
	@EnableWebMvc
	public class WebConfig implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(KamsApplication.class, args);
	}
}
