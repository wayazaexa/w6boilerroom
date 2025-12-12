package se.chasacademy.databaser.coursesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseSystemApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(CourseSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {

	}
}
