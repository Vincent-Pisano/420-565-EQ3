package com.eq3.backend.repository;

import com.eq3.backend.model.Internship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipRepository extends MongoRepository<Internship, String> {

    Optional<Internship> findByInternshipApplication_Id(String id);

    List<Internship> findByStudentEvaluationNull();

}
