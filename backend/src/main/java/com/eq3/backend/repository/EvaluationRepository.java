package com.eq3.backend.repository;

import com.eq3.backend.model.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends MongoRepository<Evaluation, String> {

    Optional<Evaluation> getByDocument_NameAndIsDisabledFalse(String name);
}
