package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.InternshipManager;
import com.pravdin.democassandra.model.Monitor;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.model.Supervisor;
import com.pravdin.democassandra.repositories.InternshipManagerRepository;
import com.pravdin.democassandra.repositories.MonitorRepository;
import com.pravdin.democassandra.repositories.StudentRepository;
import com.pravdin.democassandra.repositories.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private InternshipManagerRepository internshipManagerRepository;

    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            String email = student.getEmail();
            Optional<Student> verifStudent = studentRepository.findByEmail(email);
            if(!verifStudent.isPresent()) {
                optionalStudent = Optional.of(studentRepository.save(student));
            }
            else {
                return Optional.empty();
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            String email = monitor.getEmail();
            Optional<Monitor> verifMonitor = monitorRepository.findByEmail(email);
            if(!verifMonitor.isPresent()) {
                optionalMonitor = Optional.of(monitorRepository.save(monitor));
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            String email = supervisor.getEmail();
            Optional<Supervisor> verifSupervisor = supervisorRepository.findByEmail(email);
            if(!verifSupervisor.isPresent()) {
                optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalSupervisor;
    }

    public Optional<Student> loginStudent(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<Monitor> loginMonitor(String username, String password) {
        return monitorRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<Supervisor> loginSupervisor(String username, String password) {
        return supervisorRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<InternshipManager> loginInternshipManager(String username, String password) {
        return internshipManagerRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<List<Student>> getAllStudent(){
        Optional<List<Student>> optionalStudentList = Optional.empty();
        try {
            optionalStudentList = Optional.of(studentRepository.findAll());
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalStudentList;
    }

    public Optional<List<Monitor>> getAllMonitor(){
        Optional<List<Monitor>> optionalMonitorList = Optional.empty();
        try {
            optionalMonitorList = Optional.of(monitorRepository.findAll());
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalMonitorList;
    }

    public Optional<List<Supervisor>> getAllSupervisor(){
        Optional<List<Supervisor>> optionalSupervisorList = Optional.empty();
        try {
            optionalSupervisorList = Optional.of(supervisorRepository.findAll());
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalSupervisorList;
    }
}
