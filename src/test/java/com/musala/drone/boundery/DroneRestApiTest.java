package com.musala.drone.boundery;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import com.musala.drone.DroneServiceApplicationTests;

@SpringBootTest(
		classes = DroneServiceApplicationTests.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
class DroneRestApiTest
{

}
