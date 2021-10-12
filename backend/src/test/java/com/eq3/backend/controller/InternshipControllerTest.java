package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.InternshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    private List<InternshipApplication> expectedInternshipApplicationList;

    @Test
    //Disabled
    public void testSaveInternshipOfferWithDocument() throws Exception {
        // Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        InternshipOffer givenInternshipOffer = getInternshipOffer();
        givenInternshipOffer.setMonitor(getMonitor());

        when(service.saveInternshipOffer(
                Mockito.eq(new ObjectMapper().writeValueAsString(givenInternshipOffer)), any(MultipartFile.class))
        ).thenReturn(Optional.of(expectedInternshipOffer));

        // Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart("/save/internshipOffer")
                        .file("internshipOffer", new ObjectMapper().writeValueAsString(givenInternshipOffer).getBytes())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        // Assert
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.CREATED.value());
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithoutDocument() throws Exception {
        // Arrange
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());

        when(service.saveInternshipOffer(new ObjectMapper().writeValueAsString(expectedInternshipOffer), null))
                .thenReturn(Optional.of(expectedInternshipOffer));

        // Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart("/save/internshipOffer")
                        .file("internshipOffer", new ObjectMapper().writeValueAsString(expectedInternshipOffer).getBytes())
                        .contentType(mediaType)).andReturn();

        // Assert
        var actualInternshipOffer = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipOffer.class);
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.CREATED.value());
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
    public void testGetAllUnvalidatedInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllUnvalidatedInternshipOffer())
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/internshipOffer/unvalidated")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        var actualInternshipOffers = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testApplyInternshipOffer() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        expectedInternshipOffer = getInternshipOffer();

        expectedInternshipApplication = getInternshipApplication();
        expectedInternshipApplication.setInternshipOffer(expectedInternshipOffer);
        expectedInternshipApplication.setStudent(expectedStudent);

        when(service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer))
                .thenReturn(Optional.of(expectedInternshipApplication));

        //Act
        MvcResult result = mockMvc.perform(post("/apply/internshipOffer/" +
                expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var internshipApplication = new ObjectMapper().readValue(response.getContentAsString(), InternshipApplication.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(internshipApplication).isNotNull();
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
        MvcResult result = mockMvc.perform(post("/save/internshipOffer/validate/" +
                expectedInternshipOffer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var internshipOffer = new ObjectMapper().readValue(result.getResponse().getContentAsString(), InternshipOffer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(internshipOffer).isNotNull();
        assertThat(internshipOffer.getIsValid()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetAllTakenInternshipApplication() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();

        when(service.getAllTakenInternshipApplication())
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get("/getAll/taken/internshipApplication")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        var actualInternshipApplications = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(expectedInternshipApplicationList.size()).isEqualTo(actualInternshipApplications);
    }
}
