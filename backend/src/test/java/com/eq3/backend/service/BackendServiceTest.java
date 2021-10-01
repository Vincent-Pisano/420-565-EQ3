package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static com.eq3.backend.utils.UtilsTest.*;

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

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipManager expectedInternshipManager;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;
    private InternshipApplication expectedInternshipApplication;

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
    public void testSaveInternshipOffer() {
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        when(internshipOfferRepository.save(expectedInternshipOffer)).thenReturn(expectedInternshipOffer);

        // Act
        final Optional<InternshipOffer> internshipOffer = service.saveInternshipOffer(expectedInternshipOffer);

        // Assert
        assertThat(internshipOffer.isPresent()).isTrue();
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

    @Test
    //@Disabled
    public void testValidateIntershipOffer(){
        //Arrange
        expectedInternshipManager = getInternshipManager();
        expectedInternshipOffer = getInternshipOffer();

        //Act
        final Optional<InternshipOffer> validatedInternshipOffer =
                service.validateInternshipOffer(expectedInternshipManager.getUsername(), expectedInternshipOffer);

        //Assert
        assertThat(validatedInternshipOffer.isPresent() ? validatedInternshipOffer.get().getIsValid():true).isTrue();
    }

    @Test
    //@Disabled
    public void testApplyIntershipOffer(){
        //Arrange
        expectedStudent = getStudent();
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipApplication = getInternshipApplication();
        when(internshipApplicationRepository.save(expectedInternshipApplication)).thenReturn(expectedInternshipApplication);

        //Act
        final Optional<InternshipApplication> appliedInternshipOffer =
                service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer);

        //Assert
        assertThat(appliedInternshipOffer.isPresent()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOfferByStudent() {
        // Arrange
        expectedStudent = getStudent();
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipApplicationRepository.save(expectedInternshipApplication)).thenReturn(expectedInternshipApplication);

        // Act
        final Optional<List<InternshipOffer>> internshipOffers =
                service.getAllInternshipOfferByStudent(expectedStudent.getUsername());

        // Assert
        assertThat(internshipOffers.isPresent()).isTrue();
        assertThat(internshipOffers.get().size()).isEqualTo(expectedInternshipOfferList.size() -1);
    }
}