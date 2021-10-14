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

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsTest.getStudentWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(BackendController.class)
class BackendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BackendService service;

    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;
    private List<Supervisor> expectedSupervisorList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipOffer expectedInternshipOffer;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;
    private String expectedDocumentName;

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
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutSupervisor() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutSupervisor(Department.COMPUTER_SCIENCE))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/students/noSupervisor/" +
                Department.COMPUTER_SCIENCE)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllSupervisors() throws Exception {
        //Arrange
        expectedSupervisorList = getListOfSupervisors();
        when(service.getAllSupervisors())
                .thenReturn(Optional.of(expectedSupervisorList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/supervisors/")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSupervisorList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSupervisorList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetMonitorByUsername() throws Exception {
        //Arrange
        expectedMonitor = getMonitorWithId();
        when(service.getMonitorByUsername(expectedMonitor.getUsername()))
                .thenReturn(Optional.of(expectedMonitor));
        //Act
        MvcResult result = mockMvc.perform(get("/get/monitor/" +
                expectedMonitor.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualMonitor = new ObjectMapper().readValue(response.getContentAsString(), Monitor.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualMonitor).isNotNull();
    }

    @Test
    //@Disabled
    public void testAssignSupervisorToStudent() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedSupervisor = getSupervisorWithId();
        expectedStudent.setSupervisor(expectedSupervisor);

        when(service.assignSupervisorToStudent(expectedStudent.getId(), expectedSupervisor.getId()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(post("/assign/supervisor/" +
                expectedStudent.getId() + "/" + expectedSupervisor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudent = new ObjectMapper().readValue(response.getContentAsString(), Student.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudent.getSupervisor()).isNotNull();
    }

    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() throws Exception {
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setPDFDocument(getDocument());

        when(service.downloadInternshipOfferDocument(expectedInternshipOffer.getId()))
                .thenReturn(Optional.of(expectedInternshipOffer.getPDFDocument()));

        //Act
        MvcResult result = mockMvc.perform(get("/get/internshipOffer/document/" +
                expectedInternshipOffer.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

    @Test
    //Disabled
    public void testDownloadStudentCVDocument() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedStudent.setCVList(getCVList());
        expectedCV = getCV();

        when(service.downloadStudentCVDocument(expectedStudent.getId(), expectedCV.getId()))
                .thenReturn(Optional.ofNullable(expectedCV.getPDFDocument()));

        //Act
        MvcResult result = mockMvc.perform(get("/get/CV/document/" +
                expectedStudent.getId() + "/" + expectedCV.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testDownloadEvaluationDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedDocumentName = DOCUMENT_NAME;

        when(service.downloadEvaluationDocument(expectedDocumentName))
                .thenReturn(Optional.of(expectedPDFDocument));
        //Act
        MvcResult result = mockMvc.perform(get("/get/"+ expectedDocumentName +"/evaluation/document")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }
}