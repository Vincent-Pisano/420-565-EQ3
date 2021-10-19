package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipManager;
import com.eq3.backend.model.Supervisor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupervisorRepository extends MongoRepository<Supervisor, String> {
    Optional<Supervisor> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    List<Supervisor> findAllByIsDisabledFalse();

    Optional<Supervisor> findByUsernameAndIsDisabledFalse(String username);
}

