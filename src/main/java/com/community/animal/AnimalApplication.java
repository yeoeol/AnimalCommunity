package com.community.animal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.community.animal.post.service.PostService;

@EnableJpaAuditing
@SpringBootApplication
public class AnimalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalApplication.class, args);
	}

}
