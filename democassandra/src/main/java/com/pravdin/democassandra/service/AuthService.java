package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = Optional.of(studentRepository.save(student));
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalStudent;
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
}
