package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.bson.types.Binary;
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
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipManagerRepository internshipManagerRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    @Mock
    private InternshipRepository internshipRepository;

    //global variables
    private Student expectedStudent;
    private InternshipManager expectedInternshipManager;
    private List<Student> expectedStudentList;
    private List<Supervisor> expectedSupervisorList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipApplication> expectedInternshipApplicationList;
    private Evaluation expectedEvaluation;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;
    private Binary expectedImage;
    private Internship expectedInternship;
    private List<Internship> expectedInternshipList;

    @Test
    //@Disabled
    public void testSaveSignatureOfInternshipManager() throws IOException {
        //Arrange
        expectedImage = getImage();
        expectedInternshipManager = getInternshipManagerWithId();
        expectedInternshipManager.setSignature(expectedImage);
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(internshipManagerRepository.findByUsernameAndIsDisabledFalse(expectedInternshipManager.getUsername()))
            .thenReturn(Optional.ofNullable(expectedInternshipManager));
        when(internshipManagerRepository.save(any(InternshipManager.class)))
                .thenReturn(expectedInternshipManager);

        //Act
        Optional<Binary> optionalImage =
                service.saveSignature(expectedInternshipManager.getUsername(), multipartFile);

        //Assert
        Binary actualBinary = optionalImage.orElse(null);

        assertThat(optionalImage.isPresent()).isTrue();
        assertThat(actualBinary.getData()).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfSupervisor() throws IOException {
        //Arrange
        expectedImage = getImage();
        expectedSupervisor = getSupervisorWithId();
        expectedSupervisor.setSignature(expectedImage);
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(supervisorRepository.findByUsernameAndIsDisabledFalse(expectedSupervisor.getUsername()))
                .thenReturn(Optional.ofNullable(expectedSupervisor));
        when(supervisorRepository.save(any(Supervisor.class)))
                .thenReturn(expectedSupervisor);

        //Act
        Optional<Binary> optionalImage =
                service.saveSignature(expectedSupervisor.getUsername(), multipartFile);

        //Assert
        Binary actualBinary = optionalImage.orElse(null);

        assertThat(optionalImage.isPresent()).isTrue();
        assertThat(actualBinary.getData()).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfMonitor() throws IOException {
        //Arrange
        expectedImage = getImage();
        expectedMonitor = getMonitorWithId();
        expectedMonitor.setSignature(expectedImage);
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(monitorRepository.findByUsernameAndIsDisabledFalse(expectedMonitor.getUsername()))
                .thenReturn(Optional.ofNullable(expectedMonitor));
        when(monitorRepository.save(any(Monitor.class)))
                .thenReturn(expectedMonitor);

        //Act
        Optional<Binary> optionalImage =
                service.saveSignature(expectedMonitor.getUsername(), multipartFile);

        //Assert
        Binary actualBinary = optionalImage.orElse(null);

        assertThat(optionalImage.isPresent()).isTrue();
        assertThat(actualBinary.getData()).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfStudent() throws IOException {
        //Arrange
        expectedImage = getImage();
        expectedStudent = getStudentWithId();
        expectedStudent.setSignature(expectedImage);
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(studentRepository.findByUsernameAndIsDisabledFalse(expectedStudent.getUsername()))
                .thenReturn(Optional.ofNullable(expectedStudent));
        when(studentRepository.save(any(Student.class)))
                .thenReturn(expectedStudent);

        //Act
        Optional<Binary> optionalImage =
                service.saveSignature(expectedStudent.getUsername(), multipartFile);

        //Assert
        Binary actualBinary = optionalImage.orElse(null);

        assertThat(optionalImage.isPresent()).isTrue();
        assertThat(actualBinary.getData()).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsFromDepartment() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndDepartment(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudents(Department.COMPUTER_SCIENCE);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutSupervisor() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorIsNull(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutSupervisor(Department.COMPUTER_SCIENCE);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudents() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudents();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutCV() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndCVListIsEmpty())
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutCV();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutInterviewDate() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedStudentList);
        expectedInternshipApplicationList = getListOfInternshipApplication();
        when(internshipApplicationRepository.findAllByInterviewDateIsNotNull())
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutInterviewDate();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWaitingInterview() {
        //Arrange
        expectedStudentList = getListOfStudents();
        expectedInternshipApplicationList = getListOfInternshipApplicationWithDifferentStudent();
        when(internshipApplicationRepository.findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse())
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWaitingInterview();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllSupervisors() {
        //Arrange
        expectedSupervisorList = getListOfSupervisors();
        when(supervisorRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedSupervisorList);

        //Act
        final Optional<List<Supervisor>> optionalSupervisors =
                service.getAllSupervisors();

        //Assert
        List<Supervisor> actualSupervisors = optionalSupervisors.orElse(null);

        assertThat(optionalSupervisors.isPresent()).isTrue();
        assertThat(actualSupervisors.size()).isEqualTo(expectedSupervisorList.size());
    }

    @Test
    //@Disabled
    public void testGetMonitorByUsername() {
        //Arrange
        expectedMonitor = getMonitorWithId();

        when(monitorRepository.findByUsernameAndIsDisabledFalse(expectedMonitor.getUsername()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        final Optional<Monitor> optionalMonitor =
                service.getMonitorByUsername(expectedMonitor.getUsername());

        //Assert
        Monitor actualMonitor = optionalMonitor.orElse(null);

        assertThat(optionalMonitor.isPresent()).isTrue();
        assertThat(actualMonitor).isEqualTo(expectedMonitor);
    }

    @Test
    //@Disabled
    public void testAssignSupervisorToStudent() {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedSupervisor = getSupervisorWithId();
        expectedStudent.setSupervisor(expectedSupervisor);
        Student givenStudent = getStudentWithId();


        when(studentRepository.findById(givenStudent.getId())).thenReturn(Optional.of(givenStudent));
        when(supervisorRepository.findById(expectedSupervisor.getId())).thenReturn(Optional.of(expectedSupervisor));
        lenient().when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.assignSupervisorToStudent(givenStudent.getId(), expectedSupervisor.getId());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);

        assertThat(actualStudent).isNotNull();
        assertThat(actualStudent.getSupervisor()).isEqualTo(expectedSupervisor);
    }

    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() throws IOException {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedPDFDocument = getDocument();
        expectedInternshipOffer.setPDFDocument(getDocument());

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadInternshipOfferDocument(
                expectedInternshipOffer.getId()
        );

        //Assert
        PDFDocument actualPDFDocument = optionalDocument.orElse(null);

        assertThat(optionalDocument.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //Disabled
    public void testDownloadStudentCVDocument() throws IOException {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedStudent.setCVList(getCVList());
        expectedCV = getCV();
        expectedPDFDocument = expectedCV.getPDFDocument();

        when(studentRepository.findById(expectedStudent.getId()))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadStudentCVDocument(
                expectedStudent.getId(), expectedCV.getId()
        );

        //Assert
        PDFDocument actualPDFDocument = optionalDocument.orElse(null);

        assertThat(optionalDocument.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadEvaluationDocument() throws IOException {
        //Arrange
        expectedEvaluation = getEvaluation("student");

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadEvaluationDocument(DOCUMENT_NAME);

        //Assert
        PDFDocument actualPDFDocument = optionalDocument.orElse(null);

        assertThat(optionalDocument.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedEvaluation.getDocument());
    }

    @Test
    //@Disabled
    public void testDownloadInternshipContractDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setInternshipContract(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipContractDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipStudentEvaluationDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setStudentEvaluation(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipStudentEvaluationDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutStudentEvaluation() throws IOException {
        //Arrange
        expectedInternshipList = getInternshipList();
        expectedStudentList = getListOfStudentsWithoutStudentEvaluation();
        when(internshipRepository.findByStudentEvaluationNull())
                .thenReturn(expectedInternshipList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutStudentEvaluation();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }
}