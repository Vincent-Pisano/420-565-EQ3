package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    List<Student> findAllByIsDisabledFalseAndDepartment(Department department);
}

