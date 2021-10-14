package com.eq3.backend;

import com.eq3.backend.model.Internship;
import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.repository.InternshipApplicationRepository;
import com.eq3.backend.repository.InternshipRepository;
import com.eq3.backend.service.DocumentService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.INTERNSHIP_CONTRACT_PATH;

@SpringBootApplication
public class BackendApplication /*implements CommandLineRunner */{

    /*private DocumentService documentService;
    private InternshipRepository internshipRepository;
    private InternshipApplicationRepository internshipApplicationRepository;

    final String DOCUMENT_FILEPATH_OUTPUT = System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\contratTemplateOutput.docx";

    public BackendApplication(DocumentService documentService,
                              InternshipRepository internshipRepository,
                              InternshipApplicationRepository internshipApplicationRepository) {
        this.documentService = documentService;
        this.internshipRepository = internshipRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        Optional<InternshipApplication> optionalInternshipApplication = internshipApplicationRepository.findById("6168a6bcce3fbc6d36fdcd34");
        optionalInternshipApplication.ifPresent(internshipApplication -> {
            Internship internship = new Internship();
            internship.setInternshipApplication(internshipApplication);
            try {
                internship.setInternshipContract(getDocument());
            } catch (IOException e) {
                e.printStackTrace();
            }
            internshipRepository.save(internship);
        });

        optionalInternshipApplication.ifPresent(internshipApplication -> {
            try {
                documentService.saveInternship(internshipApplication);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public PDFDocument getDocument() throws IOException {
        Path path = Paths.get(INTERNSHIP_CONTRACT_PATH);
        return PDFDocument.builder()
                .name("contrat_stage_test.pdf")
                .content(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(path)))
                .build();
    }*/

}
