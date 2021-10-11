package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

    @InjectMocks
    private InternshipService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    //global variables
    private Student expectedStudent;
    private Monitor expectedMonitor;
    private InternshipOffer expectedInternshipOffer;
    private InternshipApplication expectedInternshipApplication;
    private List<InternshipOffer> expectedInternshipOfferList;

    @Test
    //@Disabled
    public void testSaveInternshipOfferWithDocument() throws IOException {
        //Arrange
        PDFDocument PDFDocument = getDocument();
        var multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(PDFDocument.getName());
        when(multipartFile.getBytes()).thenReturn(PDFDocument.getContent().getData());

        expectedInternshipOffer = getInternshipOffer();
        expectedInternshipOffer.setMonitor(getMonitor());
        expectedInternshipOffer.setPDFDocument(PDFDocument);

        lenient().when(internshipOfferRepository.save(any(InternshipOffer.class)))
                .thenReturn(expectedInternshipOffer);

        InternshipOffer givenInternshipOffer = getInternshipOffer();
        givenInternshipOffer.setMonitor(getMonitor());

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
        expectedInternshipOffer.setMonitor(getMonitor());
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
        expectedMonitor = getMonitor();

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
    public void testApplyInternshipOffer() {
        //Arrange
        expectedStudent = getStudent();
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
}
