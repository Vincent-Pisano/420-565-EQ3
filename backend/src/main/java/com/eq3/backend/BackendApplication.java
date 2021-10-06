package com.eq3.backend;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.StudentEvaluation;
import com.eq3.backend.repository.StudentEvaluationRepository;
import com.eq3.backend.repository.StudentRepository;
import com.eq3.backend.service.BackendService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
