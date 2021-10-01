package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.getCVList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;
import static com.eq3.backend.utils.UtilsTest.*;
import static org.mockito.ArgumentMatchers.any;

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
    private InternshipOfferRepository internshipOfferRepository;



    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipManager expectedInternshipManager;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;
    private CV expectedCV;

    @Test
    //@Disabled
    public void testSignUpStudent() {
        // Arrange
        expectedStudent = getStudent();
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        // Act
        final Optional<Student> student = service.signUp(expectedStudent);

        // Assert
        assertThat(student.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignUpMonitor() {
        // Arrange
        expectedMonitor = getMonitor();
        when(monitorRepository.save(expectedMonitor)).thenReturn(expectedMonitor);

        // Act
        final Optional<Monitor> monitor = service.signUp(expectedMonitor);

        // Assert
        assertThat(monitor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignUpSupervisor() {
        // Arrange
        expectedSupervisor = getSupervisor();
        when(supervisorRepository.save(expectedSupervisor)).thenReturn(expectedSupervisor);

        // Act
        final Optional<Supervisor> supervisor = service.signUp(expectedSupervisor);

        // Assert
        assertThat(supervisor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testLoginStudent(){
        //Arrange
        expectedStudent = getStudent();

        when(studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(expectedStudent.getUsername(), expectedStudent.getPassword()))
                .thenReturn(Optional.of(expectedStudent));
        //Act
        final Optional<Student> loginStudent =
                service.loginStudent(expectedStudent.getUsername(), expectedStudent.getPassword());

        //Assert
        assertThat(loginStudent.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testLoginMonitor(){
        //Arrange
        expectedMonitor = getMonitor();

        when(monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(expectedMonitor.getUsername(), expectedMonitor.getPassword()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        final Optional<Monitor> loginMonitor =
                service.loginMonitor(expectedMonitor.getUsername(), expectedMonitor.getPassword());

        //Assert
        assertThat(loginMonitor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testLoginSupervisor(){
        //Arrange
        expectedSupervisor = getSupervisor();

        when(supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(expectedSupervisor.getUsername(), expectedSupervisor.getPassword()))
                .thenReturn(Optional.of(expectedSupervisor));
        //Act
        final Optional<Supervisor> loginSupervisor =
                service.loginSupervisor(expectedSupervisor.getUsername(), expectedSupervisor.getPassword());

        //Assert
        assertThat(loginSupervisor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testLoginInternshipManager(){
        //Arrange
        expectedInternshipManager = getInternshipManager();

        when(internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword()))
                .thenReturn(Optional.of(expectedInternshipManager));
        //Act
        final Optional<InternshipManager> loginInternshipManager =
                service.loginInternshipManager(expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword());

        //Assert
        assertThat(loginInternshipManager.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithoutDocument(){
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        when(internshipOfferRepository.save(expectedInternshipOffer)).thenReturn(expectedInternshipOffer);

        // Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedInternshipOffer), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        // Assert
        assertThat(optionalInternshipOffer.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithDocument() throws IOException {
        // Arrange
        Document document = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(document.getName());
        when(multipartFile.getBytes()).thenReturn(document.getContent().getData());

        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        expectedInternshipOffer.setDocument(document);

        lenient().when(internshipOfferRepository.save(any(InternshipOffer.class)))
                .thenReturn(expectedInternshipOffer);

        InternshipOffer givenInternshipOffer = getInternshipOffer();
        givenInternshipOffer.setMonitor(getMonitor());

        // Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(givenInternshipOffer), multipartFile
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        // Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);
        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getDocument()).isNotNull();
    }


    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() {

    }

    @Test
    //Disabled
    public void testSaveInternshipOfferWithObjectNotMappable() {
        // Arrange

        // Act
        Optional<InternshipOffer> internshipOffer = Optional.empty();
        try {
            internshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedMonitor), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedMonitor as a String");
        }

        // Assert
        assertThat(internshipOffer.isPresent()).isFalse();
    }

    @Test
    //@Disabled
    public void testSaveCV() throws IOException {
        //Arrange
        Document document = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(document.getName());
        when(multipartFile.getBytes()).thenReturn(document.getContent().getData());

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
    public void testGetAllInternshipOfferByWorkField() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> internshipOffers =
                service.getAllInternshipOfferByWorkField(Department.COMPUTER_SCIENCE);

        // Assert
        assertThat(internshipOffers.isPresent()).isTrue();
        assertThat(internshipOffers.get().size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudents() {
        // Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndDepartment(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedStudentList);

        // Act
        final Optional<List<Student>> students =
                service.getAllStudents(Department.COMPUTER_SCIENCE);

        // Assert
        assertThat(students.isPresent()).isTrue();
        assertThat(students.get().size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetMonitorByUsername(){
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
}