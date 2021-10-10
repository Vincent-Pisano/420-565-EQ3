package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CVServiceTest {

    @InjectMocks
    private CVService service;

    @Mock
    private StudentRepository studentRepository;


    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private CV expectedCV;

    @Test
    //@Disabled
    public void testSaveCV() throws IOException {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedStudent = getStudent();
        expectedStudent.setCVList(getCVList());
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);
        when(studentRepository.findById(expectedStudent.getIdUser())).thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.saveCV(expectedStudent.getIdUser(), multipartFile);

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualStudent.getCVList().size()).isEqualTo(expectedStudent.getCVList().size());
    }

    @Test
    //@Disabled
    public void testUpdateActiveCV() throws IOException {
        //Arrange
        final int NEW_ACTIVE_CV_INDEX = 0;
        final int OLD_ACTIVE_CV_INDEX = 1;

        expectedStudent = getStudent();
        List<CV> expectedListCV = getCVList();
        CV newActiveCV = expectedListCV.get(NEW_ACTIVE_CV_INDEX);
        newActiveCV.setIsActive(true);
        expectedStudent.setCVList(expectedListCV);

        Student givenStudent = getStudent();
        List<CV> givenListCV = getCVList();
        CV oldActiveCV = expectedListCV.get(OLD_ACTIVE_CV_INDEX);
        oldActiveCV.setIsActive(true);
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(NEW_ACTIVE_CV_INDEX);

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);
        when(studentRepository.findById(givenStudent.getIdUser()))
                .thenReturn(Optional.of(givenStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.updateActiveCV(givenStudent.getIdUser(), givenCV.getId());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        List<CV> actualCVList = actualStudent != null ? actualStudent.getCVList() : null;
        CV actualCV = actualCVList != null ? actualCVList.get(NEW_ACTIVE_CV_INDEX) : null;

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualCV).isNotNull();
        assertThat(actualCV.getIsActive()).isTrue();
    }

    @Test
    //@Disabled
    public void testDeleteCV() throws IOException {
        //Arrange
        expectedStudent = getStudent();
        List<CV> expectedListCV = getCVList();
        expectedListCV.remove(0);
        expectedStudent.setCVList(expectedListCV);

        Student givenStudent = getStudent();
        List<CV> givenListCV = getCVList();
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(0);

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);
        when(studentRepository.findById(givenStudent.getIdUser()))
                .thenReturn(Optional.of(givenStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.deleteCV(givenStudent.getIdUser(), givenCV.getId());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualStudent.getCVList().size()).isEqualTo(expectedStudent.getCVList().size());
    }

    @Test
    //@Disabled
    public void testValidateCVOfStudent() throws IOException {
        //Arrange
        expectedStudent = getStudent();
        expectedCV = getCV();
        expectedCV.getPDFDocument().setContent(null);
        expectedStudent.getCVList().add(expectedCV);
        expectedCV.setIsActive(true);
        expectedCV.setStatus(CV.CVStatus.VALID);

        Student givenStudent = getStudent();
        CV givenCV = getCV();
        givenCV.getPDFDocument().setContent(null);
        givenStudent.getCVList().add(givenCV);
        givenCV.setIsActive(true);

        when(studentRepository.findById(expectedStudent.getIdUser())).thenReturn(Optional.of(givenStudent));
        lenient().when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.validateCVOfStudent(givenStudent.getIdUser());

        //Assert
        Student student = optionalStudent.orElse(null);
        CV cv = student != null ? student.getCVList().get(0) : null;
        assertThat(student).isNotNull();
        assertThat(cv).isNotNull();
        assertThat(cv.getStatus()).isEqualTo(CV.CVStatus.VALID);
    }

    @Test
    //@Disabled
    public void testGetAllStudentWithCVActiveWaitingValidation() {
        //Arrange
        expectedStudentList = getListOfStudents();

        when(studentRepository.findAllByIsDisabledFalseAndActiveCVWaitingValidation())
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> students =
                service.getAllStudentWithCVActiveWaitingValidation();

        //Assert
        assertThat(students.isPresent()).isTrue();
        assertThat(students.get().size()).isEqualTo(expectedStudentList.size());
    }
}
