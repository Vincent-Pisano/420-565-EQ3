package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipManager;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsernameAndPasswordAndIsDisabledFalse(String username, String password);

    Optional<Student> findStudentByUsernameAndIsDisabledFalse(String username);

    List<Student> findAllByIsDisabledFalseAndDepartment(Department department);

    @Query(value = "{ 'isDisabled':false ,'CVList' : {$elemMatch: { 'status': 'WAITING', 'isActive' : true} } }")
    List<Student> findAllByIsDisabledFalseAndActiveCVWaitingValidation();

    @Query(value = "{'isDisabled':false, 'department': ?0, supervisorMap:{$size: 0}}")
    List<Student> findAllByIsDisabledFalseAndDepartmentAndSupervisorMapIsEmpty(Department department);

    @Query(value = "{'isDisabled':false, CVList:{$size: 0}}")
    List<Student> findAllByIsDisabledFalseAndCVListIsEmpty();

    Optional<Student> findByUsernameAndIsDisabledFalse(String username);

    List<Student> findAllByIsDisabledFalse();

    @Query(value = "{'isDisabled':false, 'supervisorMap.id':?0}")
    List<Student> findAllBySupervisor_IdAndIsDisabledFalse(String idSupervisor);
}

