package com.cloudflare.hellourld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HellourldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellourldApplication.class, args);
	}

}
