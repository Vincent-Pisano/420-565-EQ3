package com.eq3.backend;

import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.model.Student;
import com.eq3.backend.repository.StudentRepository;
import com.eq3.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
