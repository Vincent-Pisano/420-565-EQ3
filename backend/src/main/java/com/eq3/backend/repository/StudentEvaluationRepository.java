package com.eq3.backend.repository;

import com.eq3.backend.model.StudentEvaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentEvaluationRepository extends MongoRepository<StudentEvaluation, String> {

    Optional<StudentEvaluation> findByName(String name);
}
