package com.springboot.task.newspoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.springboot.task.*"})
@EntityScan("com.springboot.task.model")
@EnableJpaRepositories("com.springboot.task.dao")
public class NewspointApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewspointApplication.class, args);
	}

}
