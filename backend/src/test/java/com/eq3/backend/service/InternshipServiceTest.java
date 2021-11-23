package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

    @InjectMocks
    private InternshipService service;

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipRepository internshipRepository;

    @Mock
    private InternshipManagerRepository internshipManagerRepository;


    //global variables
    private InternshipManager expectedInternshipManager;
    private Internship expectedInternship;
    private InternshipApplication expectedInternshipApplication;

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
                .thenReturn(Optional.of(givenInternship));
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

    @Test
    //@Disabled
    public void testDepositEnterpriseEvaluation() throws IOException {
        //Arrange
        PDFDocument pdfDocument = getDocument();
        var multipartFile = mock(MultipartFile.class);
        expectedInternship = getInternship();
        expectedInternship.setEnterpriseEvaluation(pdfDocument);

        Internship givenInternship = getInternship();

        when(multipartFile.getOriginalFilename()).thenReturn(pdfDocument.getName());
        when(multipartFile.getBytes()).thenReturn(pdfDocument.getContent().getData());
        when(internshipRepository.findById(givenInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));
        lenient().when(internshipRepository.save(any(Internship.class)))
                .thenReturn(expectedInternship);

        //Act
        Optional<Internship> optionalInternship =
                service.depositEnterpriseEvaluation(givenInternship.getId(), multipartFile);

        //Assert
        Internship actualInternshipOffer = optionalInternship.orElse(null);

        assertThat(optionalInternship.isPresent()).isTrue();
        assertThat(actualInternshipOffer.getEnterpriseEvaluation()).isNotNull();
    }
}
