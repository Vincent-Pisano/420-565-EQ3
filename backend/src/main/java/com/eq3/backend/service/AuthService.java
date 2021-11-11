package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.*;

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
            String session = getNextSessionFromDate(student.getCreationDate());
            List<String> studentSessions = student.getSessions();
            studentSessions.add(session);
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
            String session = getNextSessionFromDate(supervisor.getCreationDate());
            List<String> supervisorSessions = supervisor.getSessions();
            supervisorSessions.add(session);
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Supervisor> readmissionSupervisor(String id) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = supervisorRepository.findById(id);
            if(optionalSupervisor.isPresent()) {
                Supervisor supervisor = optionalSupervisor.get();
                List<String> supervisorSessions = supervisor.getSessions();
                Date date = new Date();
                String session = getNextSessionFromDate(date);
                supervisorSessions.add(session);
                optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
            }
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in readmission (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Student> readmissionStudent(String id) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = studentRepository.findById(id);
            if(optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                List<String> studentSessions = student.getSessions();
                Date date = new Date();
                String session = getNextSessionFromDate(date);
                studentSessions.add(session);
                optionalStudent = Optional.of(studentRepository.save(student));
            }
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in readmission (Student) : " + exception.getMessage());
        }
        return optionalStudent;
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
