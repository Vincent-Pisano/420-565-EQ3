package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.Monitor;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.model.Supervisor;
import com.pravdin.democassandra.repositories.MonitorRepository;
import com.pravdin.democassandra.repositories.StudentRepository;
import com.pravdin.democassandra.repositories.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.cassandra.repository.AllowFiltering;
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

    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = Optional.of(studentRepository.save(student));
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            optionalMonitor = Optional.of(monitorRepository.save(monitor));
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
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
