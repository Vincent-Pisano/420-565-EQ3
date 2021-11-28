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
public class InternshipOfferServiceTest {

    @InjectMocks
    private InternshipOfferService service;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;


    //global variables
    private Monitor expectedMonitor;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipOffer> expectedInternshipOfferList;

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

        when(internshipOfferRepository.save(any()))
                .thenReturn(expectedInternshipOffer);

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
    public void testGetAllUnvalidatedInternshipOffer() {
        // Arrange
        expectedInternshipOfferList = getListOfInternshipOffer();

        when(internshipOfferRepository.findAllByStatusWaitingAndIsDisabledFalseAndSession(SESSION))
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllUnvalidatedInternshipOffer(SESSION);

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

        when(internshipOfferRepository.findAllByStatusAcceptedAndIsDisabledFalseAndSession(SESSION))
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllValidatedInternshipOffer(SESSION);

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
        expectedInternshipOffer = expectedInternshipOfferList.get(0);
        expectedMonitor = getMonitorWithId();

        when(internshipOfferRepository.findAllBySessionAndMonitor_IdAndIsDisabledFalse(expectedInternshipOffer.getSession(), expectedMonitor.getId()))
                .thenReturn(expectedInternshipOfferList);

        // Act
        final Optional<List<InternshipOffer>> optionalInternshipOffers =
                service.getAllInternshipOfferOfMonitor(expectedInternshipOffer.getSession(), expectedMonitor.getId());

        // Assert
        List<InternshipOffer> actualInternshipOffers = optionalInternshipOffers.orElse(null);

        assertThat(optionalInternshipOffers.isPresent()).isTrue();
        assertThat(actualInternshipOffers.size()).isEqualTo(expectedInternshipOfferList.size());
    }

    @Test
    //@Disabled
    public void testValidateInternshipOffer() {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setStatus(InternshipOffer.OfferStatus.ACCEPTED);

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));
        when(internshipOfferRepository.save(expectedInternshipOffer))
                .thenReturn(expectedInternshipOffer);

        //Act
        final Optional<InternshipOffer> optionalInternshipOffer =
                service.validateInternshipOffer(expectedInternshipOffer.getId());

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);
        InternshipOffer.OfferStatus status = actualInternshipOffer != null ? actualInternshipOffer.getStatus() : InternshipOffer.OfferStatus.REFUSED;

        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer).isEqualTo(expectedInternshipOffer);
        assertThat(status).isEqualTo(InternshipOffer.OfferStatus.ACCEPTED);
    }

    @Test
    //@Disabled
    public void testRefuseInternshipOffer() {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setStatus(InternshipOffer.OfferStatus.REFUSED);

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));
        when(internshipOfferRepository.save(expectedInternshipOffer))
                .thenReturn(expectedInternshipOffer);

        //Act
        final Optional<InternshipOffer> optionalInternshipOffer =
                service.refuseInternshipOffer(expectedInternshipOffer.getId(), "");

        //Assert
        InternshipOffer actualInternshipOffer = optionalInternshipOffer.orElse(null);
        InternshipOffer.OfferStatus status = actualInternshipOffer != null ? actualInternshipOffer.getStatus() : InternshipOffer.OfferStatus.ACCEPTED;

        assertThat(optionalInternshipOffer.isPresent()).isTrue();
        assertThat(actualInternshipOffer).isEqualTo(expectedInternshipOffer);
        assertThat(status).isEqualTo(InternshipOffer.OfferStatus.REFUSED);
    }
}
