package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import static com.eq3.backend.utils.Utils.cleanUpStudentCVList;

import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;

    AuthService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository
    ) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
    }

    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = cleanUpStudentCVList(Optional.of(studentRepository.save(student)));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Student) : " + exception.getMessage());
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            optionalMonitor = Optional.of(monitorRepository.save(monitor));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Monitor) : " + exception.getMessage());
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Student> loginStudent(String username, String password) {
        return cleanUpStudentCVList(studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password));
    }

    public Optional<Monitor> loginMonitor(String username, String password) {
        return monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<Supervisor> loginSupervisor(String username, String password) {
        return supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<InternshipManager> loginInternshipManager(String username, String password) {
        return internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }
}
