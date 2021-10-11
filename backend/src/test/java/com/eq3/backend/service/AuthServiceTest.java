package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MonitorRepository monitorRepository;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private InternshipManagerRepository internshipManagerRepository;

    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipManager expectedInternshipManager;

    @Test
    //@Disabled
    public void testSignUpStudent() {
        //Arrange
        expectedStudent = getStudent();
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        //Act
        final Optional<Student> optionalStudent = service.signUp(expectedStudent);

        //Assert
        Student actualStudent = optionalStudent.orElse(null);

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualStudent).isEqualTo(expectedStudent);
    }

    @Test
    //@Disabled
    public void testSignUpMonitor() {
        //Arrange
        expectedMonitor = getMonitor();
        when(monitorRepository.save(expectedMonitor)).thenReturn(expectedMonitor);

        //Act
        final Optional<Monitor> optionalMonitor = service.signUp(expectedMonitor);

        //Assert
        Monitor actualMonitor = optionalMonitor.orElse(null);

        assertThat(optionalMonitor.isPresent()).isTrue();
        assertThat(actualMonitor).isEqualTo(expectedMonitor);
    }

    @Test
    //@Disabled
    public void testSignUpSupervisor() {
        //Arrange
        expectedSupervisor = getSupervisor();
        when(supervisorRepository.save(expectedSupervisor)).thenReturn(expectedSupervisor);

        //Act
        final Optional<Supervisor> optionalSupervisor = service.signUp(expectedSupervisor);

        //Assert
        Supervisor actualSupervisor = optionalSupervisor.orElse(null);

        assertThat(optionalSupervisor.isPresent()).isTrue();
        assertThat(actualSupervisor).isEqualTo(expectedSupervisor);
    }

    @Test
    //@Disabled
    public void testLoginStudent() {
        //Arrange
        expectedStudent = getStudent();

        when(studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedStudent.getUsername(), expectedStudent.getPassword()))
                .thenReturn(Optional.of(expectedStudent));
        //Act
        final Optional<Student> optionalStudent =
                service.loginStudent(expectedStudent.getUsername(), expectedStudent.getPassword());

        //Assert
        Student actualStudent = optionalStudent.orElse(null);

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(actualStudent).isEqualTo(expectedStudent);
    }

    @Test
    //@Disabled
    public void testLoginMonitor() {
        //Arrange
        expectedMonitor = getMonitor();

        when(monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedMonitor.getUsername(), expectedMonitor.getPassword()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        final Optional<Monitor> optionalMonitor =
                service.loginMonitor(expectedMonitor.getUsername(), expectedMonitor.getPassword());

        //Assert
        Monitor actualMonitor = optionalMonitor.orElse(null);

        assertThat(optionalMonitor.isPresent()).isTrue();
        assertThat(actualMonitor).isEqualTo(expectedMonitor);
    }

    @Test
    //@Disabled
    public void testLoginSupervisor() {
        //Arrange
        expectedSupervisor = getSupervisor();

        when(supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedSupervisor.getUsername(), expectedSupervisor.getPassword()))
                .thenReturn(Optional.of(expectedSupervisor));
        //Act
        final Optional<Supervisor> optionalSupervisor =
                service.loginSupervisor(expectedSupervisor.getUsername(), expectedSupervisor.getPassword());

        //Assert
        Supervisor actualSupervisor = optionalSupervisor.orElse(null);

        assertThat(optionalSupervisor.isPresent()).isTrue();
        assertThat(actualSupervisor).isEqualTo(expectedSupervisor);
    }

    @Test
    //@Disabled
    public void testLoginInternshipManager() {
        //Arrange
        expectedInternshipManager = getInternshipManager();

        when(internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(
                expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword()))
                .thenReturn(Optional.of(expectedInternshipManager));
        //Act
        final Optional<InternshipManager> optionalInternshipManager =
                service.loginInternshipManager(
                        expectedInternshipManager.getUsername(),
                        expectedInternshipManager.getPassword()
                );

        //Assert
        InternshipManager actualInternshipManager = optionalInternshipManager.orElse(null);

        assertThat(optionalInternshipManager.isPresent()).isTrue();
        assertThat(actualInternshipManager).isEqualTo(expectedInternshipManager);
    }
}
