package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student john = new Student(
                    "John",
                    "john@gmail.com",
                    LocalDate.of(2002, Month.MAY, 5));

            Student doe = new Student(
                    "doe",
                    "doe@outlook.com",
                    LocalDate.of(2001, Month.JANUARY, 10));

            repository.saveAll(List.of(john, doe));
        };
    }
}
