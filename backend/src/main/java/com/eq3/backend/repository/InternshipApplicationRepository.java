package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipApplicationRepository extends MongoRepository<InternshipApplication, String> {

<<<<<<< HEAD
    List<InternshipApplication> findAllByStatus(InternshipApplication.ApplicationStatus status);
=======
    List<InternshipApplication> findAllByStudentAndIsDisabledFalse(Student student);

>>>>>>> ead4e8a49dc951f1a1f0dbcd7250edd7ac7caa67
}
