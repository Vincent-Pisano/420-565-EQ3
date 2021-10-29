package com.eq3.backend.repository;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipApplicationRepository extends MongoRepository<InternshipApplication, String> {

    List<InternshipApplication> findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus status);

    List<InternshipApplication> findAllByStudentAndIsDisabledFalse(Student student);

    @Query(value = "{ 'isDisabled':false, 'internshipOffer.id': ?0, 'status' : { $ne: 'NOT_ACCEPTED' }}")
    List<InternshipApplication> findAllByInternshipOffer_IdAndStatusIsNotAcceptedAndIsDisabledFalse(String id);

    @Query(value = "{ 'isDisabled':false, 'status' : 'WAITING', 'interviewDate':{'$gte':new Date()}}")
    List<InternshipApplication> findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse();

    List<InternshipApplication> findAllByInterviewDateIsNotNull();
}
