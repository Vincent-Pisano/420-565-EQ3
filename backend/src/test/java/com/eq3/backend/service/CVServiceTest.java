package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
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
    private List<CV> expectedCVList;

    @Test
    //@Disabled
    public void testSaveCV() throws IOException {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedStudent = getStudentWithId();
        expectedCVList = getCVList();
        expectedStudent.setCVList(expectedCVList);

        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);
        when(studentRepository.findById(expectedStudent.getId())).thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.saveCV(expectedStudent.getId(), multipartFile);

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        List<CV> actualCVList = actualStudent != null ? actualStudent.getCVList() : null;

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualCVList).isNotNull();
        assertThat(actualCVList.size()).isEqualTo(expectedCVList.size());
    }

    @Test
    //@Disabled
    public void testDeleteCV() throws IOException {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedCVList = getCVList();
        expectedCVList.remove(0);
        expectedStudent.setCVList(expectedCVList);

        Student givenStudent = getStudentWithId();
        List<CV> givenListCV = getCVList();
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(0);

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);
        when(studentRepository.findById(givenStudent.getId()))
                .thenReturn(Optional.of(givenStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.deleteCV(givenStudent.getId(), givenCV.getId());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        List<CV> actualCVList = actualStudent != null ? actualStudent.getCVList() : null;

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualCVList).isNotNull();
        assertThat(actualCVList.size()).isEqualTo(expectedCVList.size());
    }

    @Test
    //@Disabled
    public void testUpdateActiveCV() throws IOException {
        //Arrange
        final int NEW_ACTIVE_CV_INDEX = 0;
        final int OLD_ACTIVE_CV_INDEX = 1;

        expectedStudent = getStudentWithId();
        List<CV> expectedListCV = getCVList();
        CV newActiveCV = expectedListCV.get(NEW_ACTIVE_CV_INDEX);
        newActiveCV.setIsActive(true);
        expectedStudent.setCVList(expectedListCV);

        Student givenStudent = getStudentWithId();
        List<CV> givenListCV = getCVList();
        CV oldActiveCV = expectedListCV.get(OLD_ACTIVE_CV_INDEX);
        oldActiveCV.setIsActive(true);
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(NEW_ACTIVE_CV_INDEX);

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);
        when(studentRepository.findById(givenStudent.getId()))
                .thenReturn(Optional.of(givenStudent));

        //Act
        final Optional<Student> optionalStudent =
                service.updateActiveCV(givenStudent.getId(), givenCV.getId());

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
    public void testGetAllStudentWithCVActiveWaitingValidation() {
        //Arrange
        expectedStudentList = getListOfStudents();

        when(studentRepository.findAllByIsDisabledFalseAndActiveCVWaitingValidation())
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentWithCVActiveWaitingValidation();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testValidateCVOfStudent() throws IOException {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedCV = getCV();
        expectedCV.getPDFDocument().setContent(null);
        expectedStudent.getCVList().add(expectedCV);
        expectedCV.setIsActive(true);
        expectedCV.setStatus(CV.CVStatus.VALID);

        Student givenStudent = getStudentWithId();
        CV givenCV = getCV();
        givenCV.getPDFDocument().setContent(null);
        givenStudent.getCVList().add(givenCV);
        givenCV.setIsActive(true);

        when(studentRepository.findById(expectedStudent.getId())).thenReturn(Optional.of(givenStudent));
        lenient().when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.validateCVOfStudent(givenStudent.getId());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        CV actualCV = actualStudent != null ? actualStudent.getCVList().get(0) : null;

        assertThat(actualStudent).isNotNull();
        assertThat(actualCV).isNotNull();
        assertThat(actualCV.getStatus()).isEqualTo(CV.CVStatus.VALID);
    }
}
