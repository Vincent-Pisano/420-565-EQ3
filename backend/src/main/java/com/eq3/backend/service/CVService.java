package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.eq3.backend.utils.Utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class CVService {

    private final Logger logger;

    private final StudentRepository studentRepository;

    CVService(StudentRepository studentRepository) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
    }

    public Optional<Student> saveCV(String id, MultipartFile multipartFile) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        optionalStudent = addToListCV(multipartFile, optionalStudent)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();

        return cleanUpStudentCVList(optionalStudent);
    }

    private Boolean addToListCV(MultipartFile multipartFile, Optional<Student> optionalStudent) {
        optionalStudent.ifPresent(student -> {
            List<CV> listCV = student.getCVList();
            if (listCV.size() < 10) {
                try {
                    listCV.add(new CV(extractDocument(multipartFile)));
                } catch (IOException e) {
                    logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                            + " at extractDocument in CVService : " + e.getMessage());
                }
                student.setCVList(listCV);
            }
        });
        return optionalStudent.isPresent();
    }

    public Optional<Student> deleteCV(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        optionalStudent = deleteCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
        return cleanUpStudentCVList(optionalStudent);
    }

    private Boolean deleteCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        optionalStudent.ifPresent(student -> {
            List<CV> listCV = student.getCVList();
            listCV.removeIf(cv -> cv.getId().equals(idCV));
            student.setCVList(listCV);
        });
        return optionalStudent.isPresent();
    }

    public Optional<Student> updateActiveCV(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        optionalStudent = updateActiveCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
        return cleanUpStudentCVList(optionalStudent);
    }

    public Boolean updateActiveCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        optionalStudent.ifPresent(student -> {
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                updateCVActive(idCV, cv);
            }
            student.setCVList(listCV);
        });
        return optionalStudent.isPresent();
    }

    public Optional<Student> validateCVOfStudent(String idStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);

        optionalStudent.ifPresent(student -> {
            List<CV> CVList = student.getCVList();
            CVList.forEach(cv -> {
                if (cv.getIsActive()) {
                    cv.setStatus(CV.CVStatus.VALID);
                }
            });
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);
    }

    private void updateCVActive(String idCV, CV cv) {
        if (cv.getIsActive()) {
            cv.setIsActive(false);
            if (cv.getStatus() == CV.CVStatus.WAITING)
                cv.setStatus(CV.CVStatus.INVALID);
        }
        if (cv.getId().equals(idCV)) {
            cv.setIsActive(true);
            if (cv.getStatus() != CV.CVStatus.VALID)
                cv.setStatus(CV.CVStatus.WAITING);
        }
    }

    public Optional<List<Student>> getAllStudentWithCVActiveWaitingValidation(String session) {
        List<Student> studentList =
                studentRepository.findAllByIsDisabledFalseAndActiveCVWaitingValidationAndSessionsContains(session);
        studentList.forEach(student -> cleanUpStudentCVList(Optional.ofNullable(student)));
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }
}
