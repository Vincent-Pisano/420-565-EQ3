package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class InternshipApplicationServiceTest {

    @InjectMocks
    private InternshipApplicationService service;

    @Mock
    private EmailService emailService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    //global variables
    private Student expectedStudent;
    private InternshipOffer expectedInternshipOffer;
    private InternshipApplication expectedInternshipApplication;
    private List<InternshipOffer> expectedInternshipOfferList;
    private List<InternshipApplication> expectedInternshipApplicationList;

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfStudent(){
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(expectedStudent))
                .thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllInternshipApplicationOfStudent(getSession(new Date()), expectedStudent.getUsername());

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllCompletedInternshipApplicationOfStudent(){
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.findAllByStudentAndIsDisabledFalseAndStatus(expectedStudent, InternshipApplication.ApplicationStatus.COMPLETED))
                .thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllCompletedInternshipApplicationOfStudent(getSession(new Date()), expectedStudent.getUsername());

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllWaitingInternshipApplicationOfStudent(){
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplication();
        expectedStudent = getStudentWithId();

        when(studentRepository.findStudentByUsernameAndIsDisabledFalse(expectedStudent.getUsername()))
                .thenReturn(Optional.of(expectedStudent));
        when(internshipApplicationRepository.findAllByStudentAndIsDisabledFalseAndStatus(expectedStudent, InternshipApplication.ApplicationStatus.WAITING))
                .thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllWaitingInternshipApplicationOfStudent(getSession(new Date()), expectedStudent.getUsername());

        //Assert
        List<InternshipApplication> actualInternshipApplications = optionalInternshipApplications.orElse(null);
        assertThat(optionalInternshipApplications.isPresent()).isTrue();
        assertThat(actualInternshipApplications.size()).isEqualTo(expectedInternshipApplicationList.size());
    }

    @Test
    //@Disabled
    public void testGetAllInternshipApplicationOfInternshipOffer(){
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
    public void testGetAllAcceptedInternshipApplicationsNextSessions() {
        //Arrange
        expectedInternshipApplicationList = getListOfAcceptedInternshipApplication();
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalseAndSession(any()))
                .thenReturn(expectedInternshipOfferList);
        when(internshipApplicationRepository.findAllByIsDisabledFalseAndInternshipOfferInAndStatus(
                any(), any()
        )).thenReturn(expectedInternshipApplicationList);
        //Act
        final Optional<List<InternshipApplication>> optionalInternshipApplications =
                service.getAllAcceptedInternshipApplicationsNextSessions();

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
        doNothing().when(emailService).sendEmailWhenStudentAppliesToNewInternshipOffer(any(), any());

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
    public void testUpdateInternshipApplication(){
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
}
