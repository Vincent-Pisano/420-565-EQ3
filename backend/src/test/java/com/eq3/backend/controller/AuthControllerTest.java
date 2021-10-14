package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.AuthService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipManager expectedInternshipManager;

    @Test
    //@Disabled
    public void testSignUpStudent() throws Exception {
        // Arrange
        expectedStudent = getStudentWithId();
        when(service.signUp(expectedStudent)).thenReturn(Optional.of(expectedStudent));

        // Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_UP_STUDENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudent = new ObjectMapper().readValue(response.getContentAsString(), Student.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualStudent).isEqualTo(expectedStudent);
    }

    @Test
    //@Disabled
    public void testSignUpMonitor() throws Exception {
        // Arrange
        expectedMonitor = getMonitorWithId();
        when(service.signUp(expectedMonitor)).thenReturn(Optional.of(expectedMonitor));

        // Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_UP_MONITOR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedMonitor))).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        var actualMonitor = new ObjectMapper().readValue(response.getContentAsString(), Monitor.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualMonitor).isEqualTo(expectedMonitor);
    }

    @Test
    //@Disabled
    public void testSignUpSupervisor() throws Exception {
        // Arrange
        expectedSupervisor = getSupervisorWithId();
        when(service.signUp(expectedSupervisor)).thenReturn(Optional.of(expectedSupervisor));

        // Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_UP_SUPERVISOR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedSupervisor))).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSupervisor = new ObjectMapper().readValue(response.getContentAsString(), Supervisor.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualSupervisor).isEqualTo(expectedSupervisor);
    }

    @Test
    //@Disabled
    public void testLoginStudent() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        when(service.loginStudent(expectedStudent.getUsername(), expectedStudent.getPassword()))
                .thenReturn(Optional.of(expectedStudent));
        //Act
        MvcResult result = mockMvc.perform(get( URL_LOGIN_STUDENT +
                expectedStudent.getUsername()+"/"+expectedStudent.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudent = new ObjectMapper().readValue(response.getContentAsString(), Student.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudent).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginMonitor() throws Exception {
        //Arrange
        expectedMonitor = getMonitorWithId();
        when(service.loginMonitor(expectedMonitor.getUsername(), expectedMonitor.getPassword()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        MvcResult result = mockMvc.perform(get(URL_LOGIN_MONITOR +
                expectedMonitor.getUsername()+"/"+expectedMonitor.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualMonitor = new ObjectMapper().readValue(response.getContentAsString(), Monitor.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualMonitor).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginSupervisor() throws Exception {
        //Arrange
        expectedSupervisor = getSupervisorWithId();
        when(service.loginSupervisor(expectedSupervisor.getUsername(), expectedSupervisor.getPassword()))
                .thenReturn(Optional.of(expectedSupervisor));
        //Act
        MvcResult result = mockMvc.perform(get(URL_LOGIN_SUPERVISOR +
                expectedSupervisor.getUsername()+"/"+expectedSupervisor.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSupervisor = new ObjectMapper().readValue(response.getContentAsString(), Supervisor.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSupervisor).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginInternshipManager() throws Exception {
        //Arrange
        expectedInternshipManager = getInternshipManagerWithId();
        when(service.loginInternshipManager(expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword()))
                .thenReturn(Optional.of(expectedInternshipManager));
        //Act
        MvcResult result = mockMvc.perform(get(URL_LOGIN_INTERNSHIP_MANAGER +
                expectedInternshipManager.getUsername()+"/"+expectedInternshipManager.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipManager = new ObjectMapper().readValue(response.getContentAsString(), InternshipManager.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipManager).isNotNull();
    }
}
