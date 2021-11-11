package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.types.Binary;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

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
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoCollection<Document> collection;

    @Mock
    private DistinctIterable distinctIterable;

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
    private List<String> expectedSessionList;
    private Evaluation expectedEvaluation;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;
    private Binary expectedImage;
    private Internship expectedInternship;
    private List<Internship> expectedInternshipList;
    private List<String> expectedSessionsList;
    private List<InternshipOffer> expectedInternshipOfferList;

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
        when(studentRepository.findAllByIsDisabledFalseAndDepartmentAndSessionsContains(Department.COMPUTER_SCIENCE, SESSION))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudents(Department.COMPUTER_SCIENCE, SESSION);

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
        when(studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorMapIsEmptyAndSessionContains(
                Department.COMPUTER_SCIENCE, SESSION))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutSupervisor(Department.COMPUTER_SCIENCE, SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithSupervisor() {
        //Arrange
        expectedStudentList = getListOfStudents();
        expectedSupervisor = getSupervisorWithId();
        when(studentRepository.findAllBySupervisor_IdAndIsDisabledFalse(expectedSupervisor.getId()))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithSupervisor(expectedSupervisor.getId());

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
        when(internshipApplicationRepository.findAllByInterviewDateIsNotNull())
                .thenReturn(new ArrayList<>());

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
    public void testGetAllStudentsWithInternship() {
        //Arrange
        expectedStudentList = getListOfStudents();
        expectedInternshipApplicationList = getListOfCompletedInternshipApplication();
        when(internshipApplicationRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithInternship();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed() throws ParseException {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplicationWithInterviewDate();
        expectedStudentList = Collections.singletonList(getStudentWithId());
        when( internshipApplicationRepository.findAllByInterviewDateIsNotNull()
        ).thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutStudentEvaluation() throws IOException {
        //Arrange
        expectedInternshipList = getInternshipListCompleted();
        expectedStudentList = getListOfStudentsWithoutStudentEvaluation();
        when(internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse()
        ).thenReturn(expectedInternshipList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutStudentEvaluation();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutEnterpriseEvaluation() throws IOException {
        //Arrange
        expectedInternshipList = getInternshipListCompleted();
        expectedStudentList = getListOfStudentsWithoutEnterpriseEvaluation();
        when(internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse()
        ).thenReturn(expectedInternshipList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutEnterpriseEvaluation();

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void getAllSupervisorsOfSession() {
        //Arrange
        expectedSupervisorList = getListOfSupervisors();
        when(supervisorRepository.findAllByIsDisabledFalseAndSessionsContains(SESSION))
                .thenReturn(expectedSupervisorList);

        //Act
        final Optional<List<Supervisor>> optionalSupervisors =
                service.getAllSupervisorsOfSession(SESSION);

        //Assert
        List<Supervisor> actualSupervisors = optionalSupervisors.orElse(null);

        assertThat(optionalSupervisors.isPresent()).isTrue();
        assertThat(actualSupervisors.size()).isEqualTo(expectedSupervisorList.size());
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfMonitor() {
        //Arrange
        expectedMonitor = getMonitorWithId();
        expectedSessionList = getSessionList();
        when(mongoTemplate
                .getCollection(any()))
            .thenReturn(collection);
        when(collection
                .distinct(anyString(), any(Document.class), any()))
            .thenReturn(distinctIterable);
        when(distinctIterable
                .into(new ArrayList<>()))
            .thenReturn(expectedSessionList);

        //Act
        final Optional<List<String>> optionalSessions =
                service.getAllSessionsOfMonitor(expectedMonitor.getId());

        //Assert
        List<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions.size()).isEqualTo(expectedSessionList.size());
    }

    @Test
    //@Disabled
    public void testGetAllNextSessionsOfInternshipOffers() {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();
        expectedSessionList = getSessionList();
        when(internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllNextSessionsOfInternshipOffers();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions.size()).isEqualTo(expectedSessionList.size());
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
        Map<String, Supervisor> supervisorMap = new HashMap<>();
        supervisorMap.put(SESSION, expectedSupervisor);
        expectedStudent.setSupervisorMap(supervisorMap);
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
        assertThat(actualStudent.getSupervisorMap()).isEqualTo(supervisorMap);
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
    public void testGetAllSessionsOfStudent() {
        //Arrange
        expectedSessionsList = getListOfSessions();
        expectedStudent = getStudentWithId();
        expectedInternshipApplicationList = getListOfInternshipApplication();
        when(studentRepository.findStudentByIdAndIsDisabledFalse(expectedStudent.getId()))
                .thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(expectedStudent))
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<String>> optionalSessions =
                service.getAllSessionsOfStudent(expectedStudent.getId());

        //Assert
        List<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions.size()).isEqualTo(expectedSessionsList.size());
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
    public void testDownloadInternshipEnterpriseEvaluationDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setEnterpriseEvaluation(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipEnterpriseEvaluationDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }
}