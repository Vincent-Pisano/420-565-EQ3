package com.eq3.backend;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.repository.InternshipApplicationRepository;
import com.eq3.backend.service.InternshipService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    /*private InternshipService internshipService;
    private InternshipApplicationRepository internshipApplicationRepository;

    public BackendApplication(InternshipService internshipService,
                              InternshipApplicationRepository internshipApplicationRepository) {
        this.internshipService = internshipService;
        this.internshipApplicationRepository = internshipApplicationRepository;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    public void run(String... args) throws Exception {
        /*Optional<InternshipApplication> optionalInternshipApplication
                = internshipApplicationRepository.findById("6164b22a34da0229e708be23");
        optionalInternshipApplication
                .ifPresent(internshipApplication -> {
                    System.out.println(internshipService.saveInternship(internshipApplication).get());
                });*/
    }
}
