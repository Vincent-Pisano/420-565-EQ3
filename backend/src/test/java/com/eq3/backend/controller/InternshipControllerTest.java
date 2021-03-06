package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.InternshipService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InternshipController.class)
public class InternshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipService service;

    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;
    private InternshipApplication expectedInternshipApplication;
    private Internship expectedInternship;
    private List<InternshipApplication> expectedInternshipApplicationList;
    private Map<String, String> expectedEngagements;

    @Test
    //@Disabled
    public void testSaveInternship() throws Exception {
        //Arrange
        expectedInternship = getInternship();
        expectedInternshipApplication = getInternshipApplication();

        expectedInternship.setInternshipContract(getDocument());
        expectedInternship.setInternshipApplication(expectedInternshipApplication);
        expectedInternship.setEngagements(Internship.DEFAULT_ENGAGEMENTS);

        Internship givenInternship = getInternship();
        givenInternship.setInternshipApplication(expectedInternshipApplication);
        givenInternship.setEngagements(Internship.DEFAULT_ENGAGEMENTS);

        when(service.saveInternship(givenInternship))
                .thenReturn(Optional.of(expectedInternship));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SAVE_INTERNSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(givenInternship).getBytes()))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    //@Disabled
    public void testGetEngagements() throws Exception {
        //Arrange
        expectedEngagements = Internship.DEFAULT_ENGAGEMENTS;

        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ENGAGEMENTS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualEngagements = new ObjectMapper().readValue(response.getContentAsString(), Map.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualEngagements).isNotNull();
        assertThat(expectedEngagements.size()).isEqualTo(actualEngagements.size());

    }

    @Test
    //@Disabled
    public void testGetInternshipFromInternshipApplication() throws Exception {
        //Arrange
        expectedInternship = getInternship();
        InternshipApplication givenInternshipApplication = expectedInternship.getInternshipApplication();

        when(service.getInternshipFromInternshipApplication(givenInternshipApplication.getId()))
                .thenReturn(Optional.of(expectedInternship));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_INTERNSHIP_FROM_INTERNSHIP_APPLICATION +
                givenInternshipApplication.getId()).contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternship = new ObjectMapper().readValue(response.getContentAsString(), Internship.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship).isEqualTo(expectedInternship);
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByMonitor() throws Exception {
        //Arrange
        expectedInternship = getInternship();
        expectedInternship.setSignedByMonitor(true);

        Internship givenInternship = getInternship();

        when(service.signInternshipContractByMonitor(givenInternship.getId())).thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_INTERNSHIP_CONTRACT_MONITOR + givenInternship.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternship
                = new ObjectMapper().readValue(response.getContentAsString(), Internship.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByMonitor()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByStudent() throws Exception {
        //Arrange
        expectedInternship = getInternship();
        expectedInternship.setSignedByStudent(true);

        Internship givenInternship = getInternship();

        when(service.signInternshipContractByStudent(givenInternship.getId())).thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_INTERNSHIP_CONTRACT_STUDENT + givenInternship.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternship
                = new ObjectMapper().readValue(response.getContentAsString(), Internship.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByStudent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByInternshipManager() throws Exception {
        //Arrange
        expectedInternship = getInternship();
        expectedInternship.setSignedByInternshipManager(true);

        Internship givenInternship = getInternship();

        when(service.signInternshipContractByInternshipManager(givenInternship.getId())).thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        MvcResult result = mockMvc.perform(post(URL_SIGN_INTERNSHIP_CONTRACT_INTERNSHIP_MANAGER + givenInternship.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternship
                = new ObjectMapper().readValue(response.getContentAsString(), Internship.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByInternshipManager()).isTrue();
    }

    @Test
    //Disabled
    public void testDepositStudentEvaluation() throws Exception {
        //Arrange
        PDFDocument pdfDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(pdfDocument.getName());
        when(multipartFile.getBytes()).thenReturn(pdfDocument.getContent().getData());

        expectedInternship = getInternship();
        expectedInternship.setStudentEvaluation(pdfDocument);

        Internship givenInternship = getInternship();

        when(service.depositStudentEvaluation(eq(givenInternship.getId()), any(MultipartFile.class)))
                .thenReturn(Optional.of(expectedInternship));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_DEPOSIT_INTERNSHIP_STUDENT_EVALUATION + givenInternship.getId())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    //Disabled
    public void testDepositEnterpriseEvaluation() throws Exception {
        //Arrange
        PDFDocument pdfDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(pdfDocument.getName());
        when(multipartFile.getBytes()).thenReturn(pdfDocument.getContent().getData());

        expectedInternship = getInternship();
        expectedInternship.setEnterpriseEvaluation(pdfDocument);

        Internship givenInternship = getInternship();

        when(service.depositEnterpriseEvaluation(eq(givenInternship.getId()), any(MultipartFile.class)))
                .thenReturn(Optional.of(expectedInternship));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_DEPOSIT_INTERNSHIP_ENTERPRISE_EVALUATION + givenInternship.getId())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }
}
