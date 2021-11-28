package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.types.Binary;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    private InternshipManagerRepository internshipManagerRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoCollection<Document> collection;

    @Mock
    private DistinctIterable distinctIterable;

    //global variables
    private Student expectedStudent;
    private InternshipManager expectedInternshipManager;
    private List<Student> expectedStudentList;
    private List<Supervisor> expectedSupervisorList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private List<String> expectedSessionList;
    private TreeSet<String> expectedSessionTreeSet;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;
    private Binary expectedImage;

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
                service.getAllStudentsByDepartment(Department.COMPUTER_SCIENCE, SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllSessionOfStudents() {
        //Arrange
        expectedSessionTreeSet = new TreeSet<>(Collections.singleton(SESSION));
        expectedStudentList = getListOfStudents();
        expectedStudentList.forEach(student -> student.setSessions(Collections.singletonList(SESSION)));

        when(studentRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedStudentList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllSessionOfStudents();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions).isEqualTo(expectedSessionTreeSet);
    }

    @Test
    //@Disabled
    public void testGetAllStudents() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndSessionsContains(SESSION))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsByDepartment(SESSION);

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
        when(studentRepository.findAllBySupervisor_IdAndIsDisabledFalse(new ObjectId(expectedSupervisor.getId()), SESSION))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithSupervisor(expectedSupervisor.getId(), SESSION);

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
        when(studentRepository.findAllByIsDisabledFalseAndCVListIsEmptyAndSessionsContains(SESSION))
                .thenReturn(expectedStudentList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutCV(SESSION);

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
    public void testGetSignatureStudent() throws Exception {
        //Arrange
        expectedImage = getImage();
        Student givenStudent = getStudentWithId();
        givenStudent.setSignature(expectedImage);

        when(studentRepository.findByUsernameAndIsDisabledFalse(givenStudent.getUsername()))
                .thenReturn(Optional.of(givenStudent));

        //Act
        final Optional<byte[]> optionalSignature =
                service.getSignature(givenStudent.getUsername());

        //Assert
        byte[] actualSignature = optionalSignature.orElse(null);

        assertThat(actualSignature).isNotNull();
        assertThat(actualSignature).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testGetSignatureMonitor() throws Exception {
        //Arrange
        expectedImage = getImage();
        Monitor givenMonitor = getMonitorWithId();
        givenMonitor.setSignature(expectedImage);

        when(monitorRepository.findByUsernameAndIsDisabledFalse(givenMonitor.getUsername()))
                .thenReturn(Optional.of(givenMonitor));

        //Act
        final Optional<byte[]> optionalSignature =
                service.getSignature(givenMonitor.getUsername());

        //Assert
        byte[] actualSignature = optionalSignature.orElse(null);

        assertThat(actualSignature).isNotNull();
        assertThat(actualSignature).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testGetSignatureInternshipManager() throws Exception {
        //Arrange
        expectedImage = getImage();
        InternshipManager givenInternshipManager = getInternshipManagerWithId();
        givenInternshipManager.setSignature(expectedImage);

        when(internshipManagerRepository.findByUsernameAndIsDisabledFalse(givenInternshipManager.getUsername()))
                .thenReturn(Optional.of(givenInternshipManager));

        //Act
        final Optional<byte[]> optionalSignature =
                service.getSignature(givenInternshipManager.getUsername());

        //Assert
        byte[] actualSignature = optionalSignature.orElse(null);

        assertThat(actualSignature).isNotNull();
        assertThat(actualSignature).isEqualTo(expectedImage.getData());
    }

    @Test
    //@Disabled
    public void testDeleteSignatureStudent() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        Student givenStudent = getStudentWithId();
        givenStudent.setSignature(getImage());

        when(studentRepository.findByUsernameAndIsDisabledFalse(givenStudent.getUsername()))
                .thenReturn(Optional.of(givenStudent));
        when(studentRepository.save(any()))
                .thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.deleteSignatureStudent(givenStudent.getUsername());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);

        assertThat(actualStudent).isNotNull();
        assertThat(actualStudent.getSignature()).isNull();
    }

    @Test
    //@Disabled
    public void testDeleteSignatureMonitor() throws Exception {
        //Arrange
        expectedMonitor = getMonitorWithId();
        Monitor givenMonitor = getMonitorWithId();
        givenMonitor.setSignature(getImage());

        when(monitorRepository.findByUsernameAndIsDisabledFalse(givenMonitor.getUsername()))
                .thenReturn(Optional.of(givenMonitor));
        when(monitorRepository.save(any()))
                .thenReturn(expectedMonitor);

        //Act
        final Optional<Monitor> optionalMonitor =
                service.deleteSignatureMonitor(givenMonitor.getUsername());

        //Assert
        Monitor actualMonitor = optionalMonitor.orElse(null);

        assertThat(actualMonitor).isNotNull();
        assertThat(actualMonitor.getSignature()).isNull();
    }

    @Test
    //@Disabled
    public void testDeleteSignatureInternshipManager() throws Exception {
        //Arrange
        expectedInternshipManager = getInternshipManagerWithId();
        InternshipManager givenInternshipManager = getInternshipManagerWithId();
        givenInternshipManager.setSignature(getImage());

        when(internshipManagerRepository.findByUsernameAndIsDisabledFalse(givenInternshipManager.getUsername()))
                .thenReturn(Optional.of(givenInternshipManager));
        when(internshipManagerRepository.save(any()))
                .thenReturn(expectedInternshipManager);

        //Act
        final Optional<InternshipManager> optionalInternshipManager =
                service.deleteSignatureInternshipManager(givenInternshipManager.getUsername());

        //Assert
        InternshipManager actualInternshipManager= optionalInternshipManager.orElse(null);

        assertThat(actualInternshipManager).isNotNull();
        assertThat(actualInternshipManager.getSignature()).isNull();
    }
}