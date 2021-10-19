package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipManager;
import com.eq3.backend.model.Monitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitorRepository extends MongoRepository<Monitor, String> {
    Optional<Monitor> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    Optional<Monitor> findByUsernameAndIsDisabledFalse(String username);
}
