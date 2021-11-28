package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.InternshipOfferService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InternshipOfferController.class)
class InternshipOfferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipOfferService service;

    //global variables
    private Monitor expectedMonitor;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;

    @Test
    //Disabled
    public void testSaveInternshipOfferWithDocument() throws Exception {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        InternshipOffer givenInternshipOffer = getInternshipOfferWithId();
        givenInternshipOffer.setMonitor(getMonitorWithId());

        when(service.saveInternshipOffer(
                eq(new ObjectMapper().writeValueAsString(givenInternshipOffer)), any(MultipartFile.class))
        ).thenReturn(Optional.of(expectedInternshipOffer));

        //Act
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
    public void testSaveInternshipOfferWithoutDocument() throws Exception {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());

        when(service.saveInternshipOffer(
                new ObjectMapper().writeValueAsString(expectedInternshipOffer), null))
                .thenReturn(Optional.of(expectedInternshipOffer));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart(URL_SAVE_INTERNSHIP_OFFER)
                        .file("internshipOffer",
                                new ObjectMapper().writeValueAsString(expectedInternshipOffer).getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffer
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipOffer.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expectedInternshipOffer).isEqualTo(actualInternshipOffer);
    }

    @Test
    //@Disabled
    public void getAllInternshipOfferOfMonitor() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();
        expectedInternshipOffer = expectedInternshipOfferList.get(0);
        expectedMonitor = getMonitorWithId();

        when(service.getAllInternshipOfferOfMonitor(expectedInternshipOffer.getSession(), expectedMonitor.getId()))
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_OFFERS +
                expectedInternshipOffer.getSession() + URL_MONITOR + expectedMonitor.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

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

        when(service.getAllUnvalidatedInternshipOffer(SESSION))
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS + SESSION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffers = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllValidatedInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(service.getAllValidatedInternshipOffer(SESSION))
                .thenReturn(Optional.of(expectedInternshipOfferList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS + SESSION)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffers = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
    }

    @Test
    //@Disabled
    public void testValidateInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setStatus(InternshipOffer.OfferStatus.ACCEPTED);

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
        assertThat(actualInternshipOffer.getStatus()).isEqualTo(InternshipOffer.OfferStatus.ACCEPTED);
    }

    @Test
    //@Disabled
    public void testRefuseInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setStatus(InternshipOffer.OfferStatus.REFUSED);

        when(service.refuseInternshipOffer(eq(expectedInternshipOffer.getId()), any()))
                .thenReturn(Optional.of(expectedInternshipOffer));

        //Act
        MvcResult result = mockMvc.perform(post(URL_REFUSE_INTERNSHIP_OFFER +
                expectedInternshipOffer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffer
                = new ObjectMapper().readValue(response.getContentAsString(), InternshipOffer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffer).isNotNull();
        assertThat(actualInternshipOffer.getStatus()).isEqualTo(InternshipOffer.OfferStatus.REFUSED);
    }
}