package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.Binary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
    private InternshipManager expectedInternshipManager;
    private List<Student> expectedStudentList;
    private List<Supervisor> expectedSupervisorList;
    private Monitor expectedMonitor;
    private Supervisor expectedSupervisor;
    private InternshipOffer expectedInternshipOffer;
    private Internship expectedInternship;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;
    private String expectedDocumentName;
    private Binary expectedImage;

    @Test
    //@Disabled
    public void testSaveSignatureOfInternshipManager() throws Exception {
        //Arrange
        expectedImage = getImage();
        expectedInternshipManager = getInternshipManagerWithId();
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(service.saveSignature(eq(expectedInternshipManager.getId()), any()))
        .thenReturn(Optional.of(expectedImage));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_SIGNATURE +
                        expectedInternshipManager.getId())
                        .file("signature", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfSupervisor() throws Exception {
        //Arrange
        expectedImage = getImage();
        expectedSupervisor = getSupervisorWithId();
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(service.saveSignature(eq(expectedSupervisor.getId()), any()))
                .thenReturn(Optional.of(expectedImage));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_SIGNATURE +
                        expectedSupervisor.getId())
                        .file("signature", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfMonitor() throws Exception {
        //Arrange
        expectedImage = getImage();
        expectedMonitor = getMonitorWithId();
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(service.saveSignature(eq(expectedMonitor.getId()), any()))
                .thenReturn(Optional.of(expectedImage));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_SIGNATURE +
                        expectedMonitor.getId())
                        .file("signature", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testSaveSignatureOfStudent() throws Exception {
        //Arrange
        expectedImage = getImage();
        expectedStudent = getStudentWithId();
        var multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(expectedImage.getData());
        when(service.saveSignature(eq(expectedStudent.getId()), any()))
                .thenReturn(Optional.of(expectedImage));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_SIGNATURE +
                        expectedStudent.getId())
                        .file("signature", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testGetAllStudentsFromDepartment() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudents(Department.COMPUTER_SCIENCE))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_FROM_DEPARTMENT +
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
    public void testGetAllStudents() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudents())
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS)
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
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR +
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
    public void testGetAllStudentsWithoutCV() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutCV())
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_CV)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutInterviewDate() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutInterviewDate())
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWaitingInterview() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWaitingInterview())
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WAITING_INTERVIEW)
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
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_SUPERVISORS)
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
        MvcResult result = mockMvc.perform(get(URL_GET_MONITOR +
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
        MvcResult result = mockMvc.perform(post(URL_ASSIGN_SUPERVISOR +
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
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setPDFDocument(getDocument());

        when(service.downloadInternshipOfferDocument(expectedInternshipOffer.getId()))
                .thenReturn(Optional.of(expectedInternshipOffer.getPDFDocument()));

        //Act
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT +
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
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_CV_DOCUMENT +
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
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_EVALUATION_DOCUMENT)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipContractDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();

        when(service.downloadInternshipContractDocument(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedPDFDocument));

        //Act
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_INTERNSHIP_CONTRACT + expectedInternship.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipStudentEvaluationDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();

        when(service.downloadInternshipStudentEvaluationDocument(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedPDFDocument));

        //Act
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION + expectedInternship.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutStudentEvaluation() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutStudentEvaluation())
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }
}