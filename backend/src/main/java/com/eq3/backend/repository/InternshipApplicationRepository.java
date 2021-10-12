package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipApplicationRepository extends MongoRepository<InternshipApplication, String> {

    List<InternshipApplication> findAllByStatus(InternshipApplication.ApplicationStatus status);
}
