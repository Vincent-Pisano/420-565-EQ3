package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;
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
        //Arrange
        expectedStudent = getStudent();
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> student = service.signUp(expectedStudent);

        //Assert
        assertThat(student.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignUpMonitor() {
        //Arrange
        expectedMonitor = getMonitor();
        when(monitorRepository.save(expectedMonitor)).thenReturn(expectedMonitor);

        //Act
        final Optional<Monitor> monitor = service.signUp(expectedMonitor);

        //Assert
        assertThat(monitor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignUpSupervisor() {
        //Arrange
        expectedSupervisor = getSupervisor();
        when(supervisorRepository.save(expectedSupervisor)).thenReturn(expectedSupervisor);

        //Act
        final Optional<Supervisor> supervisor = service.signUp(expectedSupervisor);

        //Assert
        assertThat(supervisor.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testLoginStudent(){
        //Arrange
        expectedStudent = getStudent();

        when(studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedStudent.getUsername(), expectedStudent.getPassword()))
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

        when(monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedMonitor.getUsername(), expectedMonitor.getPassword()))
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

        when(supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedSupervisor.getUsername(), expectedSupervisor.getPassword()))
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

        when(internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword()))
            .thenReturn(Optional.of(expectedInternshipManager));
        //Act
        final Optional<InternshipManager> loginInternshipManager =
                service.loginInternshipManager(
                        expectedInternshipManager.getUsername(),
                        expectedInternshipManager.getPassword()
                );

        //Assert
        assertThat(loginInternshipManager.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithoutDocument(){
        //Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        when(internshipOfferRepository.save(expectedInternshipOffer)).thenReturn(expectedInternshipOffer);

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedInternshipOffer), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        //Assert
        assertThat(optionalInternshipOffer.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithDocument() throws IOException {
        //Arrange
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

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(givenInternshipOffer), multipartFile
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);
        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getDocument()).isNotNull();
    }

    @Test
    //Disabled
    public void testSaveInternshipOfferWithObjectNotMappable() {
        //Arrange
        expectedMonitor = getMonitor();

        //Act
        Optional<InternshipOffer> internshipOffer = Optional.empty();
        try {
            internshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedMonitor), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedMonitor as a String");
        }

        //Assert
        assertThat(internshipOffer.isPresent()).isFalse();
    }

    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() throws IOException {
        //Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        expectedInternshipOffer.setDocument(getDocument());

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));

        //Act
        Optional<Document> optionalDocument = service.downloadInternshipOfferDocument(
                expectedInternshipOffer.getId()
        );

        //Assert
        assertThat(optionalDocument.isPresent()).isTrue();
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
        Optional<Document> optionalDocument = service.downloadStudentCVDocument(
                expectedStudent.getIdUser(), expectedCV.getId()
        );

        //Assert
        assertThat(optionalDocument.isPresent()).isTrue();
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
    public void testGetAllInternshipOfferByWorkField() {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<List<InternshipOffer>> internshipOffers =
                service.getAllInternshipOfferByWorkField(Department.COMPUTER_SCIENCE);

        //Assert
        assertThat(internshipOffers.isPresent()).isTrue();
        assertThat(internshipOffers.get().size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOffer() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByIsValidFalse())
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> internshipOffers =
                service.getAllUnvalidatedInternshipOffer();

        // Assert
        assertThat(internshipOffers.isPresent()).isTrue();
        assertThat(internshipOffers.get().size()).isEqualTo(expectedInternshipOfferList.size());
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

    @Test
    //@Disabled
    public void testValidateInternshipOffer(){
        //Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setIsValid(true);

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));
        when(internshipOfferRepository.save(expectedInternshipOffer))
                .thenReturn(expectedInternshipOffer);

        //Act
        final Optional<InternshipOffer> actualInternshipOffer =
                service.validateInternshipOffer(expectedInternshipOffer.getId());

        //Assert
        Boolean isValid = actualInternshipOffer.isPresent() ? actualInternshipOffer.get().getIsValid() : false;
        assertThat(actualInternshipOffer.isPresent()).isTrue();
        assertThat(isValid).isTrue();
    }

    @Test
    //@Disabled
    public void testApplyInternshipOffer(){
        //Arrange
        expectedStudent = getStudent();
        Student givenStudent = getStudent();
        expectedInternshipOffer = getInternshipOffer();

        List<InternshipOffer> internshipOffers = new ArrayList<>();
        internshipOffers.add(expectedInternshipOffer);
        expectedStudent.setInternshipOffers(internshipOffers);

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(givenStudent.getUsername())).thenReturn(Optional.of(givenStudent));
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent =
                service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer);

        //Assert
        Student actualStudent = optionalStudent.orElse(null);
        assertThat(actualStudent).isNotNull();
        assertThat(actualStudent.getInternshipOffers().size()).isGreaterThan(0);
    }
}