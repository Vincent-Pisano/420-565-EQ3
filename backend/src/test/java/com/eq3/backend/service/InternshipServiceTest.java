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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.lenient;

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

    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
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
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        lenient().when(internshipOfferRepository.save(any(InternshipOffer.class)))
                .thenReturn(expectedInternshipOffer);

        InternshipOffer givenInternshipOffer = getInternshipOffer();
        givenInternshipOffer.setMonitor(getMonitorWithId());

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
        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        when(internshipOfferRepository.save(expectedInternshipOffer)).thenReturn(expectedInternshipOffer);

        //Act
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try {
            optionalInternshipOffer = service.saveInternshipOffer(
                    new ObjectMapper().writeValueAsString(expectedInternshipOffer), null
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
        expectedInternship.setInternshipApplication(expectedInternshipApplication);

        when(internshipRepository.save(expectedInternship))
                .thenReturn(expectedInternship);

        //Act
        final Optional<Internship> optionalInternship =
                service.saveInternship(expectedInternshipApplication);

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
    public void testGetAllInternshipOffer() {
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
    @Disabled
    public void testGetInternshipOfferByInternshipApplication() throws IOException {
        //Arrange
        expectedInternship = getInternship();
        expectedInternshipApplication = getInternshipApplication();
        expectedInternshipOffer = getInternshipOffer();

        expectedInternshipApplication.setInternshipOffer(expectedInternshipOffer);
        expectedInternship.setInternshipApplication(expectedInternshipApplication);

        //when

        // a faire...

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
    public void testApplyInternshipOffer() {
        //Arrange
        expectedStudent = getStudentWithId();
        expectedInternshipOffer = getInternshipOffer();
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
        expectedInternshipOffer = getInternshipOffer();
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
        when(internshipApplicationRepository.save(expectedInternshipApplication))
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
}
