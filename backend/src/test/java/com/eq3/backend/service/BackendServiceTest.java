package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class BackendServiceTest {

    @InjectMocks
    private BackendService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MonitorRepository monitorRepository;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private List<Supervisor> expectedSupervisorList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipOffer expectedInternshipOffer;
    private Evaluation expectedEvaluation;
    private String expectedDocumentName;
    private CV expectedCV;


    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() throws IOException {
        //Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        expectedInternshipOffer.setPDFDocument(getDocument());

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadInternshipOfferDocument(
                expectedInternshipOffer.getId()
        );

        //Assert
        assertThat(optionalDocument.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetEvaluationDocument() throws IOException {
        //Arrange
        expectedEvaluation = getEvaluation();
        expectedDocumentName = DOCUMENT_NAME;

        when(evaluationRepository.findByName(expectedDocumentName + DOCUMENT_EXTENSION))
                .thenReturn(Optional.of(expectedEvaluation));
        //Act
        Optional<PDFDocument> optionalDocument = service.getEvaluationDocument(expectedDocumentName);

        //Assert
        assertThat(optionalDocument.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetAllStudents() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndDepartment(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> students =
                service.getAllStudents(Department.COMPUTER_SCIENCE);

        //Assert
        assertThat(students.isPresent()).isTrue();
        assertThat(students.get().size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutSupervisor() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorIsNull(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> students =
                service.getAllStudentsWithoutSupervisor(Department.COMPUTER_SCIENCE);

        //Assert
        assertThat(students.isPresent()).isTrue();
        assertThat(students.get().size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllSupervisors() {
        //Arrange
        expectedSupervisorList = getListOfSupervisors();
        when(supervisorRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedSupervisorList);

        //Act
        final Optional<List<Supervisor>> supervisors =
                service.getAllSupervisors();

        //Assert
        assertThat(supervisors.isPresent()).isTrue();
        assertThat(supervisors.get().size()).isEqualTo(expectedSupervisorList.size());
    }

    @Test
    //@Disabled
    public void testGetMonitorByUsername() {
        //Arrange
        expectedMonitor = getMonitor();

        when(monitorRepository.findByUsernameAndIsDisabledFalse(expectedMonitor.getUsername()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        final Optional<Monitor> loginMonitor =
                service.getMonitorByUsername(expectedMonitor.getUsername());

        //Assert
        assertThat(loginMonitor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testAssignSupervisorToStudent() {
        //Arrange
        expectedStudent = getStudent();
        expectedSupervisor = getSupervisor();
        expectedStudent.setSupervisor(expectedSupervisor);
        Student givenStudent = getStudent();


        when(studentRepository.findById(givenStudent.getIdUser())).thenReturn(Optional.of(givenStudent));
        when(supervisorRepository.findById(expectedSupervisor.getIdUser())).thenReturn(Optional.of(expectedSupervisor));
        lenient().when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.assignSupervisorToStudent(givenStudent.getIdUser(), expectedSupervisor.getIdUser());

        //Assert
        Student student = optionalStudent.orElse(null);
        assertThat(student).isNotNull();
        assertThat(student.getSupervisor()).isEqualTo(expectedSupervisor);
    }

    @Test
    //Disabled
    public void testDownloadStudentCVDocument() throws IOException {
        //Arrange
        expectedStudent = getStudent();
        expectedStudent.setCVList(getCVList());
        expectedCV = getCV();

        when(studentRepository.findById(expectedStudent.getIdUser()))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadStudentCVDocument(
                expectedStudent.getIdUser(), expectedCV.getId()
        );

        //Assert
        assertThat(optionalDocument.isPresent()).isTrue();
    }
}