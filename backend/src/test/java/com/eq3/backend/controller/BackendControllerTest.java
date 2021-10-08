package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;
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
import static com.eq3.backend.utils.UtilsTest.getStudent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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
    private InternshipManager expectedInternshipManager;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;
    private CV expectedCV;
    private PDFDocument expectedPDFDocument;

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
        expectedStudent = getStudent();
        expectedStudent.setCVList(getCVList());
        expectedCV = getCV();

        when(service.downloadStudentCVDocument(expectedStudent.getIdUser(), expectedCV.getId()))
                .thenReturn(Optional.ofNullable(expectedCV.getPDFDocument()));

        //Act
        MvcResult result = mockMvc.perform(get("/get/CV/document/" +
                expectedStudent.getIdUser() + "/" + expectedCV.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentLength()).isGreaterThan(0);
    }

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
        when(service.saveCV(Mockito.eq(givenStudent.getIdUser()), any(MultipartFile.class)))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "----WebKitFormBoundary");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =  mockMvc
                .perform(MockMvcRequestBuilders.multipart("/save/CV/"+ givenStudent.getIdUser())
                        .file("document", multipartFile.getBytes())
                        .contentType(mediaType)).andReturn();

        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.CREATED.value());
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

        when(service.deleteCV(givenStudent.getIdUser(), givenCV.getId()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(delete("/delete/CV/" +
                givenStudent.getIdUser() + "/" + givenCV.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
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

        when(service.updateActiveCV(givenStudent.getIdUser(), givenCV.getId()))
                .thenReturn(Optional.ofNullable(expectedStudent));

        //Act
        MvcResult result =  mockMvc
                .perform(post("/update/ActiveCV/" +
                        givenStudent.getIdUser() + "/"+ givenCV.getId())).andReturn();

        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.ACCEPTED.value());
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
        var actualStudentList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
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
        var actualSupervisorList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualSupervisorList).isNotNull();
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
    public void testApplyInternshipOffer() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        expectedInternshipOffer = getInternshipOffer();

        when(service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(post("/apply/internshipOffer/" +
                expectedStudent.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedInternshipOffer))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var student = new ObjectMapper().readValue(response.getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(student).isNotNull();
    }

    @Test
    //@Disabled
    public void testAssignSupervisorToStudent() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        expectedSupervisor = getSupervisor();
        expectedStudent.setSupervisor(expectedSupervisor);

        when(service.assignSupervisorToStudent(expectedStudent.getIdUser(), expectedSupervisor.getIdUser()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(post("/assign/supervisor/" +
                expectedStudent.getIdUser() + "/" + expectedSupervisor.getIdUser())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var student = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(student.getSupervisor()).isNotNull();
    }

    @Test
    //@Disabled
    public void testGetAllCVThatIsActiveAndNotValid() throws Exception {
        //Arrange
        expectedStudentList = getListOfStudents();

        when(service.getListStudentWithCVActiveNotValid())
                .thenReturn(Optional.of(expectedStudentList));

        //Act
        MvcResult result = mockMvc.perform(get("/getAll/student/CVActiveNotValid/")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var actualStudentList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(actualStudentList).isNotNull();
    }

    @Test
    //@Disabled
    public void testValidateCVOfStudent() throws Exception {
        //Arrange
        expectedStudent = getStudent();
        expectedCV = getCV();
        expectedCV.getPDFDocument().setContent(null);
        expectedStudent.getCVList().add(expectedCV);
        expectedCV.setIsActive(true);

        when(service.validateCVOfStudent(expectedStudent.getIdUser()))
                .thenReturn(Optional.of(expectedStudent));

        //Act
        MvcResult result = mockMvc.perform(post("/validate/CV/" + expectedStudent.getIdUser())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expectedStudent))).andReturn();

        //Assert
        MockHttpServletResponse response = result.getResponse();
        var student = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Student.class);
        List<CV> cvList = (student != null ? student.getCVList() : null);
        CV cv = (cvList != null ? cvList.get(0) : null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(student).isNotNull();
        assertThat(cvList).isNotNull();
        assertThat(cv).isNotNull();
        assertThat(cv.getIsActive()).isTrue();
    }

    @Test
    //@Disabled
    public void testGetStudentEvaluationDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        when(service.getStudentEvaluationDocument())
                .thenReturn(Optional.of(expectedPDFDocument));
        //Act
        MvcResult result = mockMvc.perform(get("/get/studentEvaluation/document")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.ACCEPTED.value());
    }

    @Test
    //@Disabled
    public void testGetEnterpriseEvaluationDocument() throws Exception {
        //Arrange
        expectedPDFDocument = getDocument();
        when(service.getEnterpriseEvaluationDocument())
                .thenReturn(Optional.of(expectedPDFDocument));
        //Act
        MvcResult result = mockMvc.perform(get("/get/enterpriseEvaluation/document")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Assert
        assertThat(result.getResponse().getStatus()).isEqualTo( HttpStatus.ACCEPTED.value());
    }
}