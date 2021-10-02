package com.eq3.backend;

import com.eq3.backend.repository.StudentRepository;
import com.eq3.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BackendService service;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(service.deleteCV("615739ad6e7e3947639f7a49", "615868b08315ed6f142c6dbe"));
    }

}
