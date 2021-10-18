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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;
    private InternshipApplication expectedInternshipApplication;
    private Internship expectedInternship;
    private List<InternshipApplication> expectedInternshipApplicationList;

    @Test
    //Disabled
    public void testSaveInternshipOfferWithDocument() throws Exception {
        // Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        InternshipOffer givenInternshipOffer = getInternshipOffer();
        givenInternshipOffer.setMonitor(getMonitorWithId());

        when(service.saveInternshipOffer(
                eq(new ObjectMapper().writeValueAsString(givenInternshipOffer)), any(MultipartFile.class))
        ).thenReturn(Optional.of(expectedInternshipOffer));

        // Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_INTERNSHIP_OFFER)
                        .file("internshipOffer",
                                new ObjectMapper().writeValueAsString(givenInternshipOffer).getBytes())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    //@Disabled
    public void testSaveInternship() throws Exception {
        // Arrange
        expectedInternship = getInternship();
        expectedInternshipApplication = getInternshipApplication();

        //expectedInternship.setInternshipApplication(expectedInternshipApplication);

        when(service.saveInternship(expectedInternshipApplication))
                .thenReturn(Optional.of(expectedInternship));

        // Act
        MvcResult result = mockMvc.perform(post(SAVE_INTERNSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternship))).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternship
                = new ObjectMapper().readValue(response.getContentAsString(), Internship.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expectedInternship).isEqualTo(actualInternship);
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithoutDocument() throws Exception {
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitorWithId());

        when(service.saveInternshipOffer(
                new ObjectMapper().writeValueAsString(expectedInternshipOffer), null))
                .thenReturn(Optional.of(expectedInternshipOffer));

        // Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_INTERNSHIP_OFFER)
                        .file("internshipOffer",
                                new ObjectMapper().writeValueAsString(expectedInternshipOffer).getBytes())
                        .contentType(mediaType)).andReturn();

        // Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffer
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipOffer.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expectedInternshipOffer).isEqualTo(actualInternshipOffer);
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOfferByWorkField() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllInternshipOfferByWorkField(Department.COMPUTER_SCIENCE))
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_OFFERS +
                Department.COMPUTER_SCIENCE.name()).contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffers = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllUnvalidatedInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllUnvalidatedInternshipOffer())
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffers = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfStudent() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(service.getAllInternshipApplicationOfStudent(expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_APPLICATIONS + expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllAcceptedInternshipApplications() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();

        when(service.getAllAcceptedInternshipApplications())
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testApplyInternshipOffer() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedInternshipOffer = getInternshipOffer();

        expectedInternshipApplication = getInternshipApplication();
        expectedInternshipApplication.setInternshipOffer(expectedInternshipOffer);
        expectedInternshipApplication.setStudent(expectedStudent);

        when(service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer))
                .thenReturn(Optional.of(expectedInternshipApplication));

        //Act
        MvcResult result = mockMvc.perform(post(URL_APPLY_INTERNSHIP_OFFER +
                expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplication
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipApplication.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplication).isNotNull();
    }

    @Test
    //@Disabled
    public void testValidateInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setIsValid(true);

        when(service.validateInternshipOffer(expectedInternshipOffer.getId()))
                .thenReturn(Optional.of(expectedInternshipOffer));

        //Act
        MvcResult result = mockMvc.perform(post(URL_VALIDATE_INTERNSHIP_OFFER +
                expectedInternshipOffer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffer
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipOffer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffer).isNotNull();
        assertThat(actualInternshipOffer.getIsValid()).isTrue();
    }

    @Test
    //@Disabled
    public void testUpdateInternshipApplication() throws Exception {
        //Arrange
        expectedInternshipApplication = getInternshipApplication();
        expectedInternshipApplication.setStatus(InternshipApplication.ApplicationStatus.ACCEPTED);

        when(service.updateInternshipApplication(expectedInternshipApplication))
                .thenReturn(Optional.of(expectedInternshipApplication));

        //Act
        MvcResult result = mockMvc.perform(post(URL_UPDATE_INTERNSHIP_APPLICATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipApplication)))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplication
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipApplication.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplication).isNotNull();
        assertThat(actualInternshipApplication.getStatus()).isEqualTo(InternshipApplication.ApplicationStatus.ACCEPTED);
    }

}
