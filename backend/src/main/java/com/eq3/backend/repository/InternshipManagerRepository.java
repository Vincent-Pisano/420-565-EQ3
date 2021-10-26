package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternshipManagerRepository extends MongoRepository<InternshipManager, String> {
    Optional<InternshipManager> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    Optional<InternshipManager> findByUsernameAndIsDisabledFalse(String username);

    Optional<InternshipManager> findByIsDisabledFalse();

}

