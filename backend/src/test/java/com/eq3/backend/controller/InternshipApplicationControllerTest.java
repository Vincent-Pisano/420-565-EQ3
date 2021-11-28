package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.InternshipApplicationService;
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

import java.util.*;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InternshipApplicationController.class)
class InternshipApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipApplicationService service;

    //global variables
    private Student expectedStudent;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOffers;
    private InternshipApplication expectedInternshipApplication;
    private List<InternshipApplication> expectedInternshipApplicationList;

    @Test
    //@Disabled
    public void testGetAllValidatedInternshipApplications() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();

        when(service.getAllValidatedInternshipApplications())
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_VALIDATED_INTERNSHIP_APPLICATIONS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfStudent() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(service.getAllInternshipApplicationOfStudent(getSession(), expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_APPLICATIONS + getSession()
                + URL_STUDENT + expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllCompletedInternshipApplicationOfStudent() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(service.getAllCompletedInternshipApplicationOfStudent(getSession(), expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS + getSession()
                + URL_STUDENT + expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllWaitingInternshipApplicationOfStudent() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(service.getAllWaitingInternshipApplicationOfStudent(getSession(), expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_WAITING_INTERNSHIP_APPLICATIONS + getSession()
                + URL_STUDENT + expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedInternshipOffer = getInternshipOfferWithId();

        when(service.getAllInternshipApplicationOfInternshipOffer(expectedInternshipOffer.getId()))
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_APPLICATIONS_BY_INTERNSHIP_OFFER
                + expectedInternshipOffer.getId())
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
    public void testGetAllAcceptedInternshipApplicationsNextSessions() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfAcceptedInternshipApplication();

        when(service.getAllAcceptedInternshipApplicationsNextSessions())
                .thenReturn(Optional.of(expectedInternshipApplicationList));
        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_APPLICATIONS_CURRENT_FUTURE_SESSIONS)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipApplications = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipApplications).isNotNull();
    }

    @Test
    //@Disabled
    public void testApplyInternshipOffer() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedInternshipOffer = getInternshipOfferWithId();

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
    public void testGetAllInternshipOfferNotAppliedOfStudentBySession() throws Exception {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedInternshipOffers = getListOfInternshipOffer();
        String session = expectedInternshipOffers.get(0).getSession();

        when(service.getAllInternshipOfferNotAppliedOfStudentBySession(expectedStudent.getUsername(),session))
                .thenReturn(Optional.of(expectedInternshipOffers));

        //Act
        MvcResult result = mockMvc.perform(get(URL_GET_ALL_INTERNSHIP_OFFERS_NOT_APPLIED_OF_STUDENT_BY_SESSION +
                session + "/" + expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualInternshipOffers
                = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualInternshipOffers).isNotNull();
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