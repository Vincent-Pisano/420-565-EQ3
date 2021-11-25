package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import static com.eq3.backend.utils.Utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class CVService {

    private final Logger logger;
    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;
    private final InternshipManagerRepository internshipManagerRepository;


    CVService(StudentRepository studentRepository, JavaMailSender mailSender, InternshipManagerRepository internshipManagerRepository) {
        this.mailSender = mailSender;
        this.internshipManagerRepository = internshipManagerRepository;
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
        updateActiveCVFromListCV(optionalStudent, idCV).ifPresent(student -> {
            sendEmailForActiveCV(student);
            studentRepository.save(student);
        });
        return cleanUpStudentCVList(optionalStudent);
    }

    private Optional<Student> updateActiveCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        optionalStudent.ifPresent(student -> {
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                setNewActiveCV(idCV, cv);
            }
            student.setCVList(listCV);
        });
        return optionalStudent;
    }

    private void setNewActiveCV(String idCV, CV cv) {
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

    private void sendEmailForActiveCV(Student student) {
        Optional<InternshipManager> optionalManager = internshipManagerRepository.findByIsDisabledFalse();
        optionalManager.ifPresent(internshipManager -> {
            try {
                createEmailForActiveCV(student, internshipManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createEmailForActiveCV(Student student, InternshipManager internshipManager) throws MessagingException {
        CV cvActive = getActiveCV(student);
        if (cvActive != null) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.addTo(internshipManager.getEmail());
            helper.setSubject(EMAIL_SUBJECT_ACTIVE_CV);
            helper.setText(getEmailTextActiveCV(student, cvActive));
            mailSender.send(message);
        }else {
            logger.error("Couldn't find active cv for student " + student.getUsername());
        }
    }

    private CV getActiveCV(Student student){
        CV cvActive = null;
        List<CV> cvList = student.getCVList();
        for (CV cv : cvList) {
            if (cv.getIsActive()){
                cvActive = cv;
                break;
            }
        }
        return cvActive;
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

    public Optional<Student> refuseCVOfStudent(String idStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);

        optionalStudent.ifPresent(student -> {
            List<CV> CVList = student.getCVList();
            CVList.forEach(cv -> {
                if (cv.getIsActive()) {
                    cv.setStatus(CV.CVStatus.REFUSED);
                }
            });
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);
    }

    public Optional<List<Student>> getAllStudentWithCVActiveWaitingValidation(String session) {
        List<Student> studentList =
                studentRepository.findAllByIsDisabledFalseAndActiveCVWaitingValidationAndSessionsContains(session);
        studentList.forEach(student -> cleanUpStudentCVList(Optional.ofNullable(student)));
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }
}
