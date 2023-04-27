package com.example.workerMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WorkerMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerMachineApplication.class, args);

	}

	/*@KafkaListener(topics = "techgeeknext-topic", groupId = "techgeeknext-group", containerFactory = "kafkaListenerContainerFactory")
	public void listen(@Payload DataForMachine user) {

		System.out.println("Received User information : " + user);
	}*/

}
