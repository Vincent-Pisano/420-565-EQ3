package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipApplicationRepository extends MongoRepository<InternshipApplication, String> {

    Optional<InternshipApplication> findByStudentUsernameAndOfferId(String studentUsername,String offerId);

    List<InternshipApplication> findByStudentUsername(String studentUsername);
}
