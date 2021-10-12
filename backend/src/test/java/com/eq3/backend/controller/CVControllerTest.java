package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.CVService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsTest.getStudent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CVController.class)
public class CVControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CVService service;

    //global variables
    private Student expectedStudent;
    private List<Student> expectedStudentList;

    @Test
    //Disabled
    public void testSaveCV() throws Exception {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedStudent = getStudent();
        expectedStudent.setCVList(getCVList());

        Student givenStudent = getStudent();
        when(service.saveCV(Mockito.eq(givenStudent.getId()), any(MultipartFile.class)))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(multipart("/save/CV/"+ givenStudent.getId())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    //Disabled
    public void testDeleteCV() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        List<CV> expectedListCV = getCVList();
        expectedListCV.remove(0);
        expectedStudent.setCVList(expectedListCV);

        Student givenStudent = getStudent();
        List<CV> givenListCV = getCVList();
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(0);

        when(service.deleteCV(givenStudent.getId(), givenCV.getId()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(delete("/delete/CV/" +
                givenStudent.getId() + "/" + givenCV.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    //@Disabled
    public void testUpdateActiveCV() throws Exception {
        //Arrange
        final int NEW_ACTIVE_CV_INDEX = 0;
        final int OLD_ACTIVE_CV_INDEX = 1;

        expectedStudent = getStudent();
        List<CV> expectedListCV = getCVList();
        CV newActiveCV = expectedListCV.get(NEW_ACTIVE_CV_INDEX);
        newActiveCV.setIsActive(true);
        expectedStudent.setCVList(expectedListCV);

        Student givenStudent = getStudent();
        List<CV> givenListCV = getCVList();
        CV oldActiveCV = expectedListCV.get(OLD_ACTIVE_CV_INDEX);
        oldActiveCV.setIsActive(true);
        givenStudent.setCVList(givenListCV);
        CV givenCV = givenListCV.get(NEW_ACTIVE_CV_INDEX);

        when(service.updateActiveCV(givenStudent.getId(), givenCV.getId()))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        MvcResult result =  mockMvc
                .perform(post("/update/ActiveCV/" +
                        givenStudent.getId() + "/"+ givenCV.getId())).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    //@Disabled
    public void testGetAllStudentWithCVActiveWaitingValidation() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();

        when(service.getAllStudentWithCVActiveWaitingValidation())
                .thenReturn(Optional.of(expectedStudentList));

        //Act
        MvcResult result = mockMvc.perform(get("/getAll/student/CVActiveNotValid/")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(response.getContentAsString(), List.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
        assertThat(actualStudentList.size()).isEqualTo(expectedStudentList.size());
    }


    @Test
    //@Disabled
    public void testValidateCVOfStudent() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        CV expectedCV = getCV();
        expectedCV.getPDFDocument().setContent(null);
        expectedStudent.getCVList().add(expectedCV);
        expectedCV.setIsActive(true);

        when(service.validateCVOfStudent(expectedStudent.getId()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(post("/validate/CV/" + expectedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudent = new ObjectMapper().readValue(response.getContentAsString(), Student.class);
        List<CV> cvList = actualStudent != null ? actualStudent.getCVList() : null;
        CV cv = cvList != null ? cvList.get(0) : null;

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudent).isNotNull();
        assertThat(cvList).isNotNull();
        assertThat(cv).isNotNull();
        assertThat(cv.getIsActive()).isTrue();
    }
}
