package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static com.eq3.backend.utils.UtilsTest.*;
import static com.eq3.backend.utils.UtilsTest.SESSION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BackendInternshipServiceTest {

    @InjectMocks
    private BackendInternshipService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private InternshipApplicationRepository internshipApplicationRepository;

    @Mock
    private InternshipOfferRepository internshipOfferRepository;

    @Mock
    private InternshipRepository internshipRepository;

    //global variables
    private List<Student> expectedStudentList;
    private InternshipOffer expectedInternshipOffer;
    private List<InternshipApplication> expectedInternshipApplicationList;
    private TreeSet<String> expectedSessionTreeSet;
    private PDFDocument expectedPDFDocument;
    private Internship expectedInternship;
    private List<Internship> expectedInternshipList;
    private List<InternshipOffer> expectedInternshipOfferList;
    private Evaluation expectedEvaluation;

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutInterviewDate() {
        //Arrange
        expectedStudentList = getListOfStudents();
        when(studentRepository.findAllByIsDisabledFalseAndSessionsContains(SESSION))
                .thenReturn(expectedStudentList);
        when(internshipApplicationRepository.findAllByInterviewDateIsNotNull())
                .thenReturn(new ArrayList<>());

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutInterviewDate(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWaitingInterview() {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplicationWithDifferentStudent();
        expectedInternshipApplicationList.forEach(internshipApplication -> {
            Student student = internshipApplication.getStudent();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            student.setSessions(Collections.singletonList(SESSION));
            internshipOffer.setSession(SESSION);
        });
        expectedStudentList = getListOfStudentsWithSessions();
        when(internshipApplicationRepository.findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse())
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWaitingInterview(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithInternship() {
        //Arrange
        expectedStudentList = getListOfStudents();
        expectedInternshipApplicationList = getListOfCompletedInternshipApplication();
        expectedInternshipApplicationList.forEach(internshipApplication -> {
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            internshipOffer.setSession(SESSION);
        });
        when(internshipApplicationRepository.findAllByIsDisabledFalse())
                .thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithInternship(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed() throws ParseException {
        //Arrange
        expectedInternshipApplicationList = getListOfInternshipApplicationWithInterviewDate();
        expectedInternshipApplicationList.forEach(internshipApplication -> {
            Student student = internshipApplication.getStudent();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            internshipOffer.setSession(SESSION);
            student.setSessions(Collections.singletonList(SESSION));
        });
        expectedStudentList = Collections.singletonList(getStudentWithIdAndSession());
        when( internshipApplicationRepository.findAllByInterviewDateIsNotNull()
        ).thenReturn(expectedInternshipApplicationList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutStudentEvaluation() throws IOException {
        //Arrange
        expectedInternshipList = getInternshipListCompleted();
        expectedInternshipList.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            internshipOffer.setSession(SESSION);
        });
        expectedStudentList = getListOfStudentsWithoutStudentEvaluation();

        when(internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse()
        ).thenReturn(expectedInternshipList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutStudentEvaluation(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllStudentsWithoutEnterpriseEvaluation() throws IOException {
        //Arrange
        expectedInternshipList = getInternshipListCompleted();
        expectedInternshipList.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            internshipOffer.setSession(SESSION);
        });
        expectedStudentList = getListOfStudentsWithoutEnterpriseEvaluation();

        when(internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse()
        ).thenReturn(expectedInternshipList);

        //Act
        final Optional<List<Student>> optionalStudents =
                service.getAllStudentsWithoutEnterpriseEvaluation(SESSION);

        //Assert
        List<Student> actualStudents = optionalStudents.orElse(null);

        assertThat(optionalStudents.isPresent()).isTrue();
        assertThat(actualStudents.size()).isEqualTo(expectedStudentList.size());
    }

    @Test
    //@Disabled
    public void testGetAllNextSessionsOfInternshipOffers() {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOfferWithDifferentSession();
        expectedSessionTreeSet = new TreeSet<>();
        expectedInternshipOfferList.forEach(internshipOffer ->
                expectedSessionTreeSet.add(internshipOffer.getSession()));
        when(internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllNextSessionsOfInternshipOffersValidated();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions.size()).isEqualTo(expectedSessionTreeSet.size());
    }

    @Test
    //@Disabled
    public void testGetAllNextSessionsOfInternshipOffersUnvalidated() {
        //Arrange
        expectedInternshipOfferList = getListOfInternshipOfferWithDifferentSession();
        expectedSessionTreeSet = new TreeSet<>();
        expectedInternshipOfferList.forEach(internshipOffer ->
                expectedSessionTreeSet.add(internshipOffer.getSession()));
        when(internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllNextSessionsOfInternshipOffersUnvalidated();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions.size()).isEqualTo(expectedSessionTreeSet.size());
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfInvalidInternshipOffers() {
        //Arrange
        expectedSessionTreeSet = new TreeSet<>(Collections.singleton(SESSION));
        expectedInternshipOfferList = getListOfInternshipOffer();
        expectedInternshipOfferList.forEach(internshipOffer ->
                internshipOffer.setSession(SESSION));

        when(internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllSessionsOfInvalidInternshipOffers();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions).isEqualTo(expectedSessionTreeSet);
    }

    @Test
    //@Disabled
    public void testGetAllSessionsOfValidInternshipOffers() {
        //Arrange
        expectedSessionTreeSet = new TreeSet<>(Collections.singleton(SESSION));
        expectedInternshipOfferList = getListOfInternshipOffer();
        expectedInternshipOfferList.forEach(internshipOffer ->
                internshipOffer.setSession(SESSION));

        when(internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse())
                .thenReturn(expectedInternshipOfferList);

        //Act
        final Optional<TreeSet<String>> optionalSessions =
                service.getAllSessionsOfValidInternshipOffers();

        //Assert
        TreeSet<String> actualSessions = optionalSessions.orElse(null);

        assertThat(optionalSessions.isPresent()).isTrue();
        assertThat(actualSessions).isEqualTo(expectedSessionTreeSet);
    }

    @Test
    //Disabled
    public void testDownloadInternshipOfferDocument() throws IOException {
        //Arrange
        expectedInternshipOffer = getInternshipOfferWithId();
        expectedInternshipOffer.setMonitor(getMonitorWithId());
        expectedPDFDocument = getDocument();
        expectedInternshipOffer.setPDFDocument(getDocument());

        when(internshipOfferRepository.findById(expectedInternshipOffer.getId()))
                .thenReturn(Optional.ofNullable(expectedInternshipOffer));

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadInternshipOfferDocument(
                expectedInternshipOffer.getId()
        );

        //Assert
        PDFDocument actualPDFDocument = optionalDocument.orElse(null);

        assertThat(optionalDocument.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipContractDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setInternshipContract(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipContractDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipStudentEvaluationDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setStudentEvaluation(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipStudentEvaluationDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadInternshipEnterpriseEvaluationDocument() throws IOException {
        //Arrange
        expectedPDFDocument = getDocument();
        expectedInternship = getInternship();
        expectedInternship.setEnterpriseEvaluation(expectedPDFDocument);

        when(internshipRepository.findById(expectedInternship.getId()))
                .thenReturn(Optional.ofNullable(expectedInternship));

        //Act
        Optional<PDFDocument> optionalContract = service.downloadInternshipEnterpriseEvaluationDocument(
                expectedInternship.getId());

        //Assert
        PDFDocument actualPDFDocument = optionalContract.orElse(null);

        assertThat(optionalContract.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedPDFDocument);
    }

    @Test
    //@Disabled
    public void testDownloadEvaluationDocument() throws IOException {
        //Arrange
        expectedEvaluation = getEvaluation("student");

        //Act
        Optional<PDFDocument> optionalDocument = service.downloadEvaluationDocument(DOCUMENT_NAME);

        //Assert
        PDFDocument actualPDFDocument = optionalDocument.orElse(null);

        assertThat(optionalDocument.isPresent()).isTrue();
        assertThat(actualPDFDocument).isEqualTo(expectedEvaluation.getDocument());
    }
}
