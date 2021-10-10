package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

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
    public void testLoginStudent() {
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
    public void testLoginMonitor() {
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
    public void testLoginSupervisor() {
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
    public void testLoginInternshipManager() {
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
}
