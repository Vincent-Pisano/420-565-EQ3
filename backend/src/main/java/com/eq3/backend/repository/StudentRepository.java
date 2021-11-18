package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    Optional<Student> findStudentByUsernameAndIsDisabledFalse(String username);

    List<Student> findAllByIsDisabledFalseAndDepartmentAndSessionsContains(Department department, String session);

    @Query(value = "{ 'isDisabled':false ,'CVList' : {$elemMatch: { 'status': 'WAITING', 'isActive' : true} }, 'sessions': ?0 }")
    List<Student> findAllByIsDisabledFalseAndActiveCVWaitingValidationAndSessionsContains(String session);

    @Query(value = "{'isDisabled':false, 'department': ?0, 'supervisorMap.?1':{'$exists' : false}, 'sessions': ?1}")
    List<Student> findAllByIsDisabledFalseAndDepartmentAndSupervisorMapIsEmptyAndSessionContains(Department department, String session);

    @Query(value = "{'isDisabled':false, CVList:{$size: 0}, 'sessions': ?0}")
    List<Student> findAllByIsDisabledFalseAndCVListIsEmptyAndSessionsContains(String session);

    Optional<Student> findByUsernameAndIsDisabledFalse(String username);

    List<Student> findAllByIsDisabledFalse();

    List<Student> findAllByIsDisabledFalseAndSessionsContains(String session);

    @Query(value = "{'isDisabled':false, 'supervisorMap.?1.$id': ?0, 'sessions': ?1}")
    List<Student> findAllBySupervisor_IdAndIsDisabledFalse(ObjectId idSupervisor, String session);
}

