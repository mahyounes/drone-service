package com.musala.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
		basePackages = "com.musala")
@ComponentScan(
		basePackages = "com.musala")
@EnableAutoConfiguration(
		exclude =
		{ SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@SpringBootConfiguration
public class DroneServiceApplicationTests
{

	public static void main(final String[] args)
	{
		SpringApplication.run(DroneServiceApplicationTests.class, args);
	}

}
