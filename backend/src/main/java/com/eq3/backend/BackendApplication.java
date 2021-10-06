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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    StudentEvaluationRepository studentEvaluationRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(studentEvaluationRepository.findAll().size() < 1 ) {
            StudentEvaluation studentEvaluation = new StudentEvaluation();
            Path pdfPath = Paths.get(System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\documentTest.pdf");
            PDFDocument evaluation = new PDFDocument();
            evaluation.setName("documentTest.pdf");
            evaluation.setContent(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(pdfPath)));
            studentEvaluation.setPDFDocument(evaluation);
            studentEvaluation.setName("initialForm");

            studentEvaluationRepository.save(studentEvaluation);
        }

    }

}
