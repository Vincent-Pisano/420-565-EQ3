package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsURL.URL_SIGN_INTERNSHIP_CONTRACT_MONITOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

    @InjectMocks
    private InternshipService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipRepository internshipRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    @Mock
    private InternshipManagerRepository internshipManagerRepository;


    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
    private InternshipManager expectedInternshipManager;
    private InternshipOffer expectedInternshipOffer;
    private Internship expectedInternship;
    private InternshipApplication expectedInternshipApplication;
    private List<InternshipOffer> expectedInternshipOfferList;

    private List<InternshipApplication> expectedInternshipApplicationList;

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithDocument() throws IOException {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        InternshipOffer givenInternshipOffer = getInternshipOfferWithoutId();
        givenInternshipOffer.setMonitor(getMonitorWithId());

        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());
        lenient().when(internshipOfferRepository.save(any(InternshipOffer.class)))
                .thenReturn(expectedInternshipOffer);

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(givenInternshipOffer), multipartFile
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);

        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getPDFDocument()).isNotNull();
    }

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithoutDocument() {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        InternshipOffer givenInternshipOffer = getInternshipOfferWithoutId();

        when(internshipOfferRepository.save(givenInternshipOffer)).thenReturn(expectedInternshipOffer);

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(givenInternshipOffer), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedInternshipOffer as a String");
        }

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);

        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getPDFDocument()).isNull();
    }

    @Test
    //Disabled
    public void testSaveInternshipOfferWithObjectNotMappable() {
        //Arrange
        expectedMonitor = getMonitorWithId();

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedMonitor), null
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Couldn't write expectedMonitor as a String");
        }

        //Assert
        assertThat(optionalInternshipOffer.isPresent()).isFalse();
    }

    @Test
    //@Disabled
    public void testSaveInternship() throws IOException {
        //Arrange
        expectedInternship = getInternship();
        expectedInternshipApplication = getInternshipApplication();

        expectedInternship.setInternshipContract(getDocument());
        expectedInternship.setInternshipApplication(expectedInternshipApplication);
        expectedInternship.setEngagements(Internship.DEFAULT_ENGAGEMENTS);

        Internship givenInternship = getInternship();
        givenInternship.setInternshipApplication(expectedInternshipApplication);
        givenInternship.setEngagements(Internship.DEFAULT_ENGAGEMENTS);

        when(internshipRepository.save(givenInternship))
                .thenReturn(expectedInternship);
        when(internshipManagerRepository.findByIsDisabledFalse())
                .thenReturn(Optional.of(getInternshipManagerWithId()));

        //Act
        final Optional<Internship> optionalInternship =
                service.saveInternship(givenInternship);

        //Assert
        Internship actualInternship = optionalInternship.orElse(null);

        assertThat(optionalInternship.isPresent()).isTrue();
        assertThat(actualInternship).isEqualTo(expectedInternship);
    }

    @Test
    //@Disabled
    public void testGetInternshipFromInternshipApplication() throws IOException {
        //Arrange
        expectedInternship = getInternship();
        InternshipApplication givenInternshipApplication = expectedInternship.getInternshipApplication();

        when(internshipRepository.findByInternshipApplication_Id(givenInternshipApplication.getId()))
                .thenReturn(Optional.of(expectedInternship));

        //Act
        final Optional<Internship> optionalInternship =
                service.getInternshipFromInternshipApplication(givenInternshipApplication.getId());

        //Assert
        Internship actualInternship = optionalInternship.orElse(null);

        assertThat(optionalInternship.isPresent()).isTrue();
        assertThat(actualInternship).isEqualTo(expectedInternship);
    }

    @Test
    //@Disabled
    public void testGetAllInternshipOfferByWorkField() {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByWorkFieldAndIsValidTrueAndIsDisabledFalse(Department.COMPUTER_SCIENCE))
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllInternshipOfferByWorkField(Department.COMPUTER_SCIENCE);

        //Assert
        List<InternshipOffer> actualInternshipOffers = optionalInternshipOffers.orElse(null);

        assertThat(optionalInternshipOffers.isPresent()).isTrue();
        assertThat(actualInternshipOffers.size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testGetAllUnvalidatedInternshipOffer() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllUnvalidatedInternshipOffer();

        // Assert
        List<InternshipOffer> actualInternshipOffers = optionalInternshipOffers.orElse(null);

        assertThat(optionalInternshipOffers.isPresent()).isTrue();
        assertThat(actualInternshipOffers.size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testGetAllValidatedInternshipOffer() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllValidatedInternshipOffer();

        // Assert
        List<InternshipOffer> actualInternshipOffers = optionalInternshipOffers.orElse(null);

        assertThat(optionalInternshipOffers.isPresent()).isTrue();
        assertThat(actualInternshipOffers.size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void getAllInternshipOfferOfMonitor() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();
        expectedMonitor = getMonitorWithId();

        when(internshipOfferRepository.findAllByMonitor_IdAndIsDisabledFalse(expectedMonitor.getId()))
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllInternshipOfferOfMonitor(expectedMonitor.getId());

        // Assert
        List<InternshipOffer> actualInternshipOffers = optionalInternshipOffers.orElse(null);

        assertThat(optionalInternshipOffers.isPresent()).isTrue();
        assertThat(actualInternshipOffers.size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfStudent() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(expectedStudent))
                .thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllInternshipApplicationOfStudent(expectedStudent.getUsername());

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfInternshipOffer() throws Exception {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedInternshipOffer = getInternshipOfferWithId();

        when(internshipApplicationRepository.findAllByInternshipOffer_IdAndStatusIsNotAcceptedAndIsDisabledFalse(
                expectedInternshipOffer.getId()))
                .thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllInternshipApplicationOfInternshipOffer(expectedInternshipOffer.getId());

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllAcceptedInternshipApplications() {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();

        when(internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.ACCEPTED))
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllAcceptedInternshipApplications();

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());

    }

    @Test
    //@Disabled
    public void testGetAllValidatedInternshipApplications() {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();

        when(internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.VALIDATED))
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllValidatedInternshipApplications();

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());

    }

    @Test
    //@Disabled
    public void testApplyInternshipOffer() {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipApplication = getInternshipApplication();

        expectedInternshipApplication.setInternshipOffer(expectedInternshipOffer);
        expectedInternshipApplication.setStudent(expectedStudent);

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(expectedStudent.getUsername())).thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.save(any(InternshipApplication.class))).thenReturn(expectedInternshipApplication);

        //Act
        final Optional<InternshipApplication> optionalInternshipApplication =
                service.applyInternshipOffer(expectedStudent.getUsername(), expectedInternshipOffer);

        //Assert
        InternshipApplication actualInternshipApplication = optionalInternshipApplication.orElse(null);

        assertThat(optionalInternshipApplication.isPresent()).isTrue();
        assertThat(actualInternshipApplication).isEqualTo(expectedInternshipApplication);
    }

    @Test
    //@Disabled
    public void testValidateInternshipOffer() {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setIsValid(true);

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));
        when(internshipOfferRepository.save(expectedInternshipOffer))
                .thenReturn(expectedInternshipOffer);

        //Act
        final Optional<InternshipOffer> optionalInternshipOffer =
                service.validateInternshipOffer(expectedInternshipOffer.getId());

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);
        Boolean actualIsValid = actualInternshipOffer != null ? actualInternshipOffer.getIsValid() : false;

        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer).isEqualTo(expectedInternshipOffer);
        assertThat(actualIsValid).isTrue();
    }

    @Test
    //@Disabled
    public void testUpdateInternshipApplication() throws Exception {
        //Arrange
        expectedInternshipApplication = getInternshipApplication();
        InternshipApplication givenInternshipApplication = getInternshipApplication();
        expectedInternshipApplication.setStatus(InternshipApplication.ApplicationStatus.ACCEPTED);

        when(internshipApplicationRepository.findById(expectedInternshipApplication.getId()))
                .thenReturn(Optional.of(givenInternshipApplication));
        when(internshipApplicationRepository.save(any()))
                .thenReturn(expectedInternshipApplication);

        //Act
        final Optional<InternshipApplication> optionalInternshipApplication =
                service.updateInternshipApplication(expectedInternshipApplication);

        //Assert
        InternshipApplication actualInternshipApplication = optionalInternshipApplication.orElse(null);
        InternshipApplication.ApplicationStatus actualStatus = actualInternshipApplication != null ? actualInternshipApplication.getStatus() : null;

        assertThat(optionalInternshipApplication.isPresent()).isTrue();
        assertThat(actualInternshipApplication).isEqualTo(expectedInternshipApplication);
        assertThat(actualStatus).isEqualTo(expectedInternshipApplication.getStatus());
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByMonitor() throws Exception {
        //Arrange
        expectedInternship = getInternshipWithInternshipContract();
        expectedInternship.setSignedByMonitor(true);

        Internship givenInternship = getInternshipWithInternshipContract();
        InternshipApplication internshipApplication = givenInternship.getInternshipApplication();
        InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
        Monitor monitor = internshipOffer.getMonitor();
        monitor.setSignature(getImage());

        when(internshipRepository.findById(givenInternship.getId()))
                .thenReturn(Optional.of(givenInternship));
        lenient().when(internshipRepository.save(any(Internship.class)))
                .thenReturn(expectedInternship);

        //Act
        Optional<Internship> optionalInternship =
                service.signInternshipContractByMonitor(givenInternship.getId());

        //Assert
        Internship actualInternship = optionalInternship.orElse(null);

        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByMonitor()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByStudent() throws IOException {
        //Arrange
        expectedInternship = getInternshipWithInternshipContract();
        expectedInternship.setSignedByStudent(true);

        Internship givenInternship = getInternshipWithInternshipContract();
        InternshipApplication internshipApplication = givenInternship.getInternshipApplication();
        Student student = internshipApplication.getStudent();
        student.setSignature(getImage());

        when(internshipRepository.findById(givenInternship.getId()))
                .thenReturn(Optional.ofNullable(givenInternship));
        lenient().when(internshipRepository.save(any(Internship.class)))
                .thenReturn(expectedInternship);
        //Act
        Optional<Internship> optionalInternship =
                service.signInternshipContractByStudent(givenInternship.getId());

        //Assert
        Internship actualInternship = optionalInternship.orElse(null);

        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByStudent()).isTrue();
    }

    @Test
    //@Disabled
    public void testSignInternshipContractByInternshipManager() throws Exception {
        //Arrange
        expectedInternship = getInternshipWithInternshipContract();
        expectedInternship.setSignedByInternshipManager(true);
        InternshipApplication expectedInternshipApplication = expectedInternship.getInternshipApplication();
        expectedInternshipApplication.setStatus(InternshipApplication.ApplicationStatus.COMPLETED);

        Internship givenInternship = getInternshipWithInternshipContract();

        expectedInternshipManager = getInternshipManagerWithId();
        expectedInternshipManager.setSignature(getImage());

        when(internshipManagerRepository.findByIsDisabledFalse())
                .thenReturn(Optional.of(expectedInternshipManager));
        when(internshipRepository.findById(givenInternship.getId()))
                .thenReturn(Optional.of(givenInternship));
        when(internshipApplicationRepository.save(any(InternshipApplication.class)))
                .thenReturn(expectedInternshipApplication);
        lenient().when(internshipRepository.save(any(Internship.class)))
                .thenReturn(expectedInternship);

        //Act
        Optional<Internship> optionalInternship =
                service.signInternshipContractByInternshipManager(givenInternship.getId());

        //Assert
        Internship actualInternship = optionalInternship.orElse(null);
        InternshipApplication actualInternshipApplication = actualInternship != null ? actualInternship.getInternshipApplication() : null;

        assertThat(actualInternship).isNotNull();
        assertThat(actualInternship.isSignedByInternshipManager()).isTrue();
        assertThat(actualInternshipApplication.getStatus()).isEqualTo(InternshipApplication.ApplicationStatus.COMPLETED);
    }

    @Test
    //@Disabled
    public void testDepositStudentEvaluation() throws IOException {
        //Arrange
        PDFDocument pdfDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        expectedInternship = getInternship();
        expectedInternship.setStudentEvaluation(pdfDocument);

        Internship givenInternship = getInternship();

        when(multipartFile.getOriginalFilename()).thenReturn(pdfDocument.getName());
        when(multipartFile.getBytes()).thenReturn(pdfDocument.getContent().getData());
        when(internshipRepository.findById(givenInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));
        lenient().when(internshipRepository.save(any(Internship.class)))
                .thenReturn(expectedInternship);

        //Act
        Optional<Internship> optionalInternship =
                service.depositStudentEvaluation(givenInternship.getId(), multipartFile);

        //Assert
        Internship actualInternshipOffer = optionalInternship.orElse(null);

        assertThat(optionalInternship.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getStudentEvaluation()).isNotNull();
    }
}
