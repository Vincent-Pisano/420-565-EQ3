package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;
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

import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.getInternshipOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static com.eq3.backend.utils.UtilsTest.*;

@WebMvcTest(BackendController.class)
class BackendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BackendService service;

    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipManager expectedInternshipManager;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;

    @Test
    public void testSignUpStudent() throws Exception {
        // Arrange
        expectedStudent = getStudent();
        when(service.signUp(expectedStudent)).thenReturn(Optional.of(expectedStudent));

        // Act
        MvcResult result = mockMvc.perform(post("/signUp/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        // Assert
        var actualStudent = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(expectedStudent).isEqualTo(actualStudent);
    }

    @Test
    public void testSignUpMonitor() throws Exception {
        // Arrange
        expectedMonitor = getMonitor();
        when(service.signUp(expectedMonitor)).thenReturn(Optional.of(expectedMonitor));

        // Act
        MvcResult result = mockMvc.perform(post("/signUp/monitor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedMonitor))).andReturn();

        // Assert
        var actualMonitor = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Monitor.class);
        assertThat(expectedMonitor).isEqualTo(actualMonitor);
    }

    @Test
        public void testSignUpSupervisor() throws Exception {
        // Arrange
        expectedSupervisor = getSupervisor();
        when(service.signUp(expectedSupervisor)).thenReturn(Optional.of(expectedSupervisor));

        // Act
        MvcResult result = mockMvc.perform(post("/signUp/supervisor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedSupervisor))).andReturn();

        // Assert
        var actualSupervisor = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Supervisor.class);
        assertThat(expectedSupervisor).isEqualTo(actualSupervisor);
    }

    @Test
    //@Disabled
    public void testLoginStudent() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        when(service.loginStudent(expectedStudent.getUsername(), expectedStudent.getPassword()))
                .thenReturn(Optional.of(expectedStudent));
        //Act
        MvcResult result = mockMvc.perform(get("/login/student/" +
                        expectedStudent.getUsername()+"/"+expectedStudent.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudent = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudent).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginSupervisor() throws Exception {
        //Arrange
        expectedSupervisor = getSupervisor();
        when(service.loginSupervisor(expectedSupervisor.getUsername(), expectedSupervisor.getPassword()))
                .thenReturn(Optional.of(expectedSupervisor));
        //Act
        MvcResult result = mockMvc.perform(get("/login/supervisor/" +
                expectedSupervisor.getUsername()+"/"+expectedSupervisor.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSupervisor = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Supervisor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSupervisor).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginMonitor() throws Exception {
        //Arrange
        expectedMonitor = getMonitor();
        when(service.loginMonitor(expectedMonitor.getUsername(), expectedMonitor.getPassword()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        MvcResult result = mockMvc.perform(get("/login/monitor/" +
                expectedMonitor.getUsername()+"/"+expectedMonitor.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualMonitor = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Monitor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualMonitor).isNotNull();
    }

    @Test
    //@Disabled
    public void testLoginInternshipManager() throws Exception {
        //Arrange
        expectedInternshipManager = getInternshipManager();
        when(service.loginInternshipManager(expectedInternshipManager.getUsername(), expectedInternshipManager.getPassword()))
                .thenReturn(Optional.of(expectedInternshipManager));
        //Act
        MvcResult result = mockMvc.perform(get("/login/internshipManager/" +
                expectedInternshipManager.getUsername()+"/"+expectedInternshipManager.getPassword())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipManager = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipManager.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipManager).isNotNull();
    }

    @Test
    public void testSaveInternshipOffer() throws Exception {
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        when(service.saveInternshipOffer(expectedInternshipOffer)).thenReturn(Optional.of(expectedInternshipOffer));

        // Act
        MvcResult result = mockMvc.perform(post("/save/internshipOffer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        // Assert
        var internshipOffer = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipOffer.class);
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.CREATED.value());
        System.out.println(expectedInternshipOffer.equals(internshipOffer));
        assertThat(expectedInternshipOffer).isEqualTo(internshipOffer);
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOfferByWorkField() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllInternshipOfferByWorkField(Department.COMPUTER_SCIENCE))
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/internshipOffer/" +
                Department.COMPUTER_SCIENCE.name()).contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        var actualInternshipOffers = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllUnvalidatedInternshipOffer())
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/internshipOffer")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        var actualInternshipOffers = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudents() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudents(Department.COMPUTER_SCIENCE))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/students/" +
                Department.COMPUTER_SCIENCE)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetMonitorByUsername() throws Exception {
        //Arrange
        expectedMonitor = getMonitor();
        when(service.getMonitorByUsername(expectedMonitor.getUsername()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        MvcResult result = mockMvc.perform(get("/get/monitor/" +
                expectedMonitor.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualMonitor = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Monitor.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualMonitor).isNotNull();
    }

    @Test
    //@Disabled
    public void testValidateInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipManager = getInternshipManager();
        expectedInternshipOffer = getInternshipOffer();
        when(service.validateInternshipOffer(expectedInternshipManager.getUsername(), expectedInternshipOffer))
                .thenReturn(Optional.of(expectedInternshipOffer));

        //Act
        MvcResult result = mockMvc.perform(post("/save/internshipOffer/validate/" +
                expectedInternshipManager.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var internshipOffer = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipOffer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(internshipOffer).isNotNull();
    }

    //@Test
    //@Disabled
    /*public void testApplyInternshipOffer() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        expectedInternshipOffer = getInternshipOffer();
        expectedIntershipApplication = getInternshipApplication();
        when(service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer))
                .thenReturn(Optional.of(expectedIntershipApplication));

        //Act
        MvcResult result = mockMvc.perform(post("/apply/internshipOffer/" +
                expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var internshipApplication = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipApplication.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(internshipApplication).isNotNull();
    }*/
}