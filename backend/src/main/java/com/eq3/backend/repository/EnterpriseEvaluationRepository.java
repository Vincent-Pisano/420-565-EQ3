package com.eq3.backend.repository;

import com.eq3.backend.model.EnterpriseEvaluation;
import com.eq3.backend.model.StudentEvaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnterpriseEvaluationRepository extends MongoRepository<EnterpriseEvaluation, String> {

    Optional<EnterpriseEvaluation> findByName(String name);
}
