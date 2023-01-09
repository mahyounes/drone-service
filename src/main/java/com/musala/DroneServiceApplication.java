package com.musala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.musala.mock.MockRunner;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class DroneServiceApplication implements CommandLineRunner {

	@Autowired
	MockRunner mockRunner;

	public static void main(final String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.mockRunner.createMockData();
	}

}
