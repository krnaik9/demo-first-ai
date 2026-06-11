package com.first.ai.demo_first_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class DemoFirstAiApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoFirstAiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ChatClient.Builder builder) {

		return args -> {

			ChatClient chatClient = builder.build();

			String logContent = Files.readString(
					Paths.get("src/main/resources/error.log"));

			String response = chatClient.prompt()
					.user("""
     				
					 Rules:
							- severity must be LOW, MEDIUM, HIGH, or CRITICAL
							- rootCause max 5 words
							- fix max 5 words
							- prevention max 5 words					

					{
					  "severity":"",
					  "rootCause":"",
					  "fix":"",
					  "prevention":""
					}
              
                    Exception:
                    """ + logContent)
					.call()
					.content();

			System.out.println("\n========== AI ANALYSIS ==========");
			System.out.println(response);
			System.out.println("================================\n");

		};
	}
}
