package com.eq3.backend;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.StudentEvaluation;
import com.eq3.backend.repository.StudentEvaluationRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final StudentEvaluationRepository studentEvaluationRepository;

    public BackendApplication(StudentEvaluationRepository studentEvaluationRepository) {
        this.studentEvaluationRepository = studentEvaluationRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StudentEvaluation studentEvaluation = new StudentEvaluation();
        Path pdfPath = Paths.get(System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\documentTest.pdf");
        PDFDocument document = PDFDocument.builder()
                .name("studentEvaluation.pdf")
                .content(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(pdfPath)))
                .build();
        studentEvaluation.setDocument(document);
        this.studentEvaluationRepository.save(studentEvaluation);
    }

}
