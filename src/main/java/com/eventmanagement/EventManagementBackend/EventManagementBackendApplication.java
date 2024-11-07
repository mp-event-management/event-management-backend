package com.eventmanagement.EventManagementBackend;

import com.eventmanagement.EventManagementBackend.infrastructure.config.JwtConfigProperties;
import com.eventmanagement.EventManagementBackend.infrastructure.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfigProperties.class, RsaKeyConfigProperties.class})
public class EventManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementBackendApplication.class, args);
	}

}

//development branch