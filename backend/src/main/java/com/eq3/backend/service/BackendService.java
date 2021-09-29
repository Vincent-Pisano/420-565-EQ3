package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackendService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final InternshipOfferRepository internshipOfferRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   InternshipOfferRepository internshipOfferRepository) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.internshipOfferRepository = internshipOfferRepository;
    }


    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = Optional.of(studentRepository.save(student));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Student) : " + exception.getMessage());
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            optionalMonitor = Optional.of(monitorRepository.save(monitor));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Monitor) : " + exception.getMessage());
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Student> loginStudent(String username, String password) {
        return studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<List<Student>> getAllStudents(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartment(department);
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<Supervisor> loginSupervisor(String username, String password) {
        return supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<Monitor> loginMonitor(String username, String password) {
        return monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<InternshipManager> loginInternshipManager(String username, String password) {
        return internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<InternshipOffer> saveInternshipOffer(InternshipOffer internshipOffer){
        return Optional.of(internshipOfferRepository.save(internshipOffer));
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(workField);
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }
    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalse();
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }


    public Optional<Monitor> getMonitorByUsername(String username){
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String username, InternshipOffer internshipOffer){
        Optional<InternshipManager> internshipManager = internshipManagerRepository.findByUsernameAndIsDisabledFalse(username);
        if(internshipManager.isPresent()){
            if (internshipOffer != null){
                internshipOffer.setIsValid(true);
                return Optional.of(internshipOfferRepository.save(internshipOffer));
            }
        }
        return Optional.empty();
    }
}

