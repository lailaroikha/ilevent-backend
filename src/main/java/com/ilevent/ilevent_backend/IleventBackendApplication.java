package com.ilevent.ilevent_backend;

import com.ilevent.ilevent_backend.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@Log
public class IleventBackendApplication implements CommandLineRunner  {
//	implements CommandLineRunner

	public static void main(String[] args) {

		SpringApplication.run(IleventBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Ilevent Backend Application started successfully");
	}
}
