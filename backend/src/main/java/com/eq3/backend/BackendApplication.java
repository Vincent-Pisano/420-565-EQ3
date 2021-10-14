package com.eq3.backend;

import com.eq3.backend.service.DocumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    public DocumentService documentService;

    final String DOCUMENT_FILEPATH_INPUT = System.getProperty("user.dir") + "\\src\\main\\ressources\\assets\\documentStage.docx";
    final String DOCUMENT_FILEPATH_OUTPUT = System.getProperty("user.dir") + "\\src\\main\\ressources\\assets\\documentStageOutput.docx";

    public BackendApplication(DocumentService documentService) {
        this.documentService = documentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        documentService.updateDocument(DOCUMENT_FILEPATH_INPUT, DOCUMENT_FILEPATH_OUTPUT);

    }

}
