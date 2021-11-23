package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendInternshipService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsTest.getListOfSessions;
import static com.eq3.backend.utils.UtilsURL.*;
import static com.eq3.backend.utils.UtilsURL.URL_GET_ALL_STUDENTS_WITHOUT_ENTERPRISE_EVALUATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BackendInternshipController.class)
class BackendInternshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BackendInternshipService service;

    //global variables
    private List<Student> expectedStudentList;
    private List<String> expectedSessionList;
    private TreeSet<String> expectedSessionTreeSet;
    private PDFDocument expectedPDFDocument;
    private InternshipOffer expectedInternshipOffer;
    private Internship expectedInternship;
    private String expectedDocumentName;

    @Test
    //@Disabled
    public void testGetAllStudentsWithInternship() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithInternship(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITH_INTERNSHIP + SESSION)
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
        when(service.getAllStudentsWithoutInterviewDate(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE + SESSION)
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
        when(service.getAllStudentsWaitingInterview(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WAITING_INTERVIEW + SESSION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutStudentEvaluation() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutStudentEvaluation(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION + SESSION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(
                URL_GET_ALL_STUDENTS_WITH_APPLICATION_STATUS_WAITING_AND_INTERVIEW_DATE_PASSED_TODAY + SESSION
        )
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfInternshipOffers() throws Exception {
        //Arrange
        expectedSessionList = getListOfSessions();
        when(service.getAllNextSessionsOfInternshipOffersValidated())
                .thenReturn(Optional.of(new TreeSet<>(expectedSessionList)));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSessionList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSessionList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfInternshipOffersUnvalidated() throws Exception {
        //Arrange
        expectedSessionList = getListOfSessions();
        when(service.getAllNextSessionsOfInternshipOffersUnvalidated())
                .thenReturn(Optional.of(new TreeSet<>(expectedSessionList)));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS_UNVALIDATED)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSessionList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSessionList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfInvalidInternshipOffers() throws Exception {
        //Arrange
        expectedSessionTreeSet = new TreeSet<>(Collections.singleton(SESSION));

        when(service.getAllSessionsOfInvalidInternshipOffers())
                .thenReturn(Optional.of(expectedSessionTreeSet));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_SESSION_OF_INVALID_INTERNSHIP_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSessionList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSessionList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfValidInternshipOffers() throws Exception {
        //Arrange
        expectedSessionTreeSet = new TreeSet<>(Collections.singleton(SESSION));

        when(service.getAllSessionsOfValidInternshipOffers())
                .thenReturn(Optional.of(expectedSessionTreeSet));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_SESSION_OF_VALID_INTERNSHIP_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualSessionList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSessionList).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutEnterpriseEvaluation() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(service.getAllStudentsWithoutEnterpriseEvaluation(SESSION))
                .thenReturn(Optional.of(expectedStudentList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_STUDENTS_WITHOUT_ENTERPRISE_EVALUATION + SESSION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
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
    public void testDownloadInternshipEnterpriseEvaluationDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();

        when(service.downloadInternshipEnterpriseEvaluationDocument(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedPDFDocument));

        //Act
        MvcResult result = mockMvc.perform(get(URL_DOWNLOAD_INTERNSHIP_ENTERPRISE_EVALUATION + expectedInternship.getId())
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
}
