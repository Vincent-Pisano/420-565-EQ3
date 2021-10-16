package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.eq3.backend.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.eq3.backend.utils.Utils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itextpdf.text.pdf.PdfWriter;

@Service
public class InternshipService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRepository internshipRepository;
    private final InternshipManagerRepository internshipManagerRepository;

    InternshipService(StudentRepository studentRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipApplicationRepository internshipApplicationRepository,
                   InternshipRepository internshipRepository,
                   InternshipManagerRepository internshipManagerRepository
    ) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
        this.internshipManagerRepository = internshipManagerRepository;
    }

    public Optional<InternshipOffer> saveInternshipOffer(String internshipOfferJson, MultipartFile multipartFile) {
        InternshipOffer internshipOffer = null;
        try {
            internshipOffer = getInternshipOffer(internshipOfferJson, multipartFile);
        } catch (IOException e) {
            logger.error("Couldn't map the string internshipOffer to InternshipOffer.class at " +
                    "saveInternshipOffer in InternshipService : " + e.getMessage());
        }
        return internshipOffer == null ? Optional.empty() :
                Optional.of(internshipOfferRepository.save(internshipOffer));
    }

    private InternshipOffer getInternshipOffer(String InternshipOfferJson, MultipartFile multipartFile) throws IOException {
        InternshipOffer internshipOffer = mapInternshipOffer(InternshipOfferJson);
        if (multipartFile != null) {
            try {
                internshipOffer.setPDFDocument(extractDocument(multipartFile));
            } catch (IOException e) {
                logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                        + " at extractDocument in InternshipService : " + e.getMessage());
            }
        }
        return internshipOffer;
    }

    private InternshipOffer mapInternshipOffer(String internshipOfferJson) throws IOException {
        return new ObjectMapper().readValue(internshipOfferJson, InternshipOffer.class);
    }

    public Optional<Internship> saveInternship(Internship internship) {
        internshipApplicationRepository.save(internship.getInternshipApplication());
        internship.setInternshipContract(getContract(internship));
        return Optional.of(internshipRepository.save(internship));
    }

    public Optional<Map<String, String>> getDefaultEngagements() {
        return Optional.of(Internship.DEFAULT_ENGAGEMENTS);
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByWorkFieldAndIsValidTrueAndIsDisabledFalse(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setPDFDocument(
                internshipOffer.getPDFDocument() != null ? new PDFDocument() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse();
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfStudent(String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        return optionalStudent.map(internshipApplicationRepository::findAllByStudentAndIsDisabledFalse);
    }

    public Optional<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.ACCEPTED);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<InternshipApplication> applyInternshipOffer(String studentUsername, InternshipOffer internshipOffer) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        return optionalStudent.map(student -> createInternshipApplication(student, internshipOffer));
    }

    private InternshipApplication createInternshipApplication(Student student, InternshipOffer internshipOffer){
        InternshipApplication internshipApplication = new InternshipApplication();
        internshipApplication.setInternshipOffer(internshipOffer);
        internshipApplication.setStudent(student);
        return internshipApplicationRepository.save(internshipApplication);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String idOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(idOffer);
        optionalInternshipOffer.ifPresent(internshipOffer -> internshipOffer.setIsValid(true));
        return optionalInternshipOffer.map(internshipOfferRepository::save);
    }

    public Optional<InternshipApplication> updateInternshipApplication(InternshipApplication internshipApplication) {
        Optional<InternshipApplication> optionalInternshipApplication =
                internshipApplicationRepository.findById(internshipApplication.getId());

        return optionalInternshipApplication.map((_internshipApplication) ->
                internshipApplicationRepository.save(internshipApplication));
    }

    private PDFDocument getContract(Internship internship) {
        InternshipApplication internshipApplication = internship.getInternshipApplication();

        Document document = new Document();
        PDFDocument pdfDocument = new PDFDocument();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            Font largeBold = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD);
            Font mediumBold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font standard = new Font(Font.FontFamily.HELVETICA, 12);

            Paragraph paragEmpty = new Paragraph(" ");
            paragEmpty.setSpacingAfter(325f);

            Paragraph paragTitle = new Paragraph("CONTRAT DE STAGE", largeBold);
            paragTitle.setSpacingAfter(350f);
            paragTitle.setAlignment(Element.ALIGN_CENTER);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Paragraph paragDate = new Paragraph(LocalDate.now().format(formatter));
            paragDate.setAlignment(Element.ALIGN_CENTER);

            document.add(paragEmpty);
            document.add(paragTitle);
            document.add(paragDate);

            document.newPage();

            Paragraph paragEntente = new Paragraph("ENTENTE DE STAGE INTERVENUE ENTRE LES PARTIES SUIVANTES", mediumBold);
            paragEntente.setAlignment(Element.ALIGN_CENTER);
            paragEntente.setSpacingAfter(50f);
            document.add(paragEntente);

            Paragraph paragCadre = new Paragraph("Dans le cadre de la formule ATE, les parties citées ci-dessous :", standard);
            paragCadre.setAlignment(Element.ALIGN_CENTER);
            document.add(paragCadre);

            Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByIsDisabledFalse();
            Paragraph paragCadreInternshipOwner = new Paragraph("Le gestionnaire de stage, " + optionalInternshipManager.get().getFirstName() + " " + optionalInternshipManager.get().getLastName() + ",", standard);
            paragCadreInternshipOwner.setAlignment(Element.ALIGN_CENTER);
            paragCadreInternshipOwner.setSpacingAfter(40f);
            document.add(paragCadreInternshipOwner);

            Paragraph paragAnd = new Paragraph("et", mediumBold);
            paragAnd.setAlignment(Element.ALIGN_CENTER);
            paragAnd.setSpacingAfter(40f);
            document.add(paragAnd);

            Paragraph paragCadreMonitor = new Paragraph("L’employeur, " + internshipApplication.getInternshipOffer().getMonitor().getFirstName() + " " + internshipApplication.getInternshipOffer().getMonitor().getLastName() + ",", standard);
            paragCadreMonitor.setAlignment(Element.ALIGN_CENTER);
            paragCadreMonitor.setSpacingAfter(40f);
            document.add(paragCadreMonitor);

            document.add(paragAnd);

            Paragraph paragCadreStudent = new Paragraph("L’étudiant(e), " + internshipApplication.getStudent().getFirstName() + " " + internshipApplication.getStudent().getLastName() + ",", standard);
            paragCadreStudent.setAlignment(Element.ALIGN_CENTER);
            paragCadreStudent.setSpacingAfter(40f);
            document.add(paragCadreStudent);

            Paragraph paragCadreConditions = new Paragraph("Conviennent des conditions de stage suivantes :", standard);
            paragCadreConditions.setAlignment(Element.ALIGN_CENTER);
            paragCadreStudent.setSpacingAfter(10f);
            document.add(paragCadreConditions);

            Paragraph paragEmptySmall = new Paragraph(" ");
            document.add(paragEmptySmall);

            float[] pointColumnWidths = {150F};
            PdfPTable table = new PdfPTable(pointColumnWidths);

            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Chunk c = new Chunk("ENDROIT DU STAGE", fontHeader);
            Paragraph paragraphHeader = new Paragraph(c);

            PdfPCell cell1 = new PdfPCell(paragraphHeader);
            cell1.setBackgroundColor(new BaseColor(230,230,230));
            cell1.setUseVariableBorders(true);
            cell1.setBorderWidthBottom(0f);
            cell1.setPadding(7);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Adresse : " + internshipApplication.getInternshipOffer().getAddress() + ", " + internshipApplication.getInternshipOffer().getCity() + ", " + internshipApplication.getInternshipOffer().getPostalCode()));
            cell2.setUseVariableBorders(true);
            cell2.setBorderWidthTop(0f);
            cell2.setPadding(7);

            Chunk c2 = new Chunk("DURÉE DU STAGE", fontHeader);
            Paragraph paragraphDuration = new Paragraph(c2);
            PdfPCell cell3 = new PdfPCell(paragraphDuration);
            cell3.setBackgroundColor(new BaseColor(230,230,230));
            cell3.setUseVariableBorders(true);
            cell3.setBorderWidthBottom(0f);
            cell3.setPadding(7);

            PdfPCell cell4 = new PdfPCell(new Paragraph(String.format("Date de début : " + internshipApplication.getInternshipOffer().getStartDate(), formatter)));
            cell4.setUseVariableBorders(true);
            cell4.setBorderWidthTop(0f);
            cell4.setPadding(7);

            PdfPCell cell5 = new PdfPCell(new Paragraph(String.format("Date de fin : " + internshipApplication.getInternshipOffer().getEndDate(), formatter)));
            cell5.setUseVariableBorders(true);
            cell5.setBorderWidthTop(0f);
            cell5.setPadding(7);

            InternshipOffer.WorkShift Workshift = internshipApplication.getInternshipOffer().getWorkShift();

            Duration diff = Duration.between(internshipApplication.getInternshipOffer().getStartDate().toInstant(), internshipApplication.getInternshipOffer().getEndDate().toInstant());
            long diffWeeks = diff.toDays() / 7;
            long diffDays = diff.toDays() % 7;

            PdfPCell cell6 = new PdfPCell(new Paragraph("Nombre de semaines : " + diffWeeks + (diffDays != 0 ? " (et " + diffDays + " jours)" : "")));
            cell6.setUseVariableBorders(true);
            cell6.setBorderWidthTop(0f);
            cell6.setPadding(7);

            Chunk c3 = new Chunk("HORAIRE DE TRAVAIL", fontHeader);
            Paragraph paragraphWorkshift = new Paragraph(c3);

            PdfPCell cell7 = new PdfPCell(paragraphWorkshift);
            cell7.setBackgroundColor(new BaseColor(230,230,230));
            cell7.setUseVariableBorders(true);
            cell7.setBorderWidthBottom(0f);
            cell7.setPadding(7);

            PdfPCell cell8 = new PdfPCell(new Paragraph("Type d'horaire: " + (Workshift == InternshipOffer.WorkShift.DAY ? "Jour" : Workshift == InternshipOffer.WorkShift.NIGHT ? "Nuit" : "Flexibe")));
            cell8.setUseVariableBorders(true);
            cell8.setBorderWidthTop(0f);
            cell8.setPadding(7);

            PdfPCell cell9 = new PdfPCell(new Paragraph("Nombre d'heures total par semaine: " + internshipApplication.getInternshipOffer().getWeeklyWorkTime() + " heures"));
            cell9.setUseVariableBorders(true);
            cell9.setBorderWidthTop(0f);
            cell9.setPadding(7);

            Chunk c4 = new Chunk("SALAIRE", fontHeader);
            Paragraph paragraphSalary = new Paragraph(c4);

            PdfPCell cell10 = new PdfPCell(paragraphSalary);
            cell10.setBackgroundColor(new BaseColor(230,230,230));
            cell10.setUseVariableBorders(true);
            cell10.setBorderWidthBottom(0f);
            cell10.setPadding(7);

            PdfPCell cell11 = new PdfPCell(new Paragraph("Salaire horaire: " + String.format("%.2f",internshipApplication.getInternshipOffer().getHourlySalary()) + "$"));
            cell11.setUseVariableBorders(true);
            cell11.setBorderWidthTop(0f);
            cell11.setPadding(7);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell10);
            table.addCell(cell11);

            document.add(table);

            document.newPage();

            Paragraph paragEngagements = new Paragraph("TACHES ET RESPONSABILITES DU STAGIAIRE", mediumBold);
            paragEngagements.setAlignment(Element.ALIGN_CENTER);
            paragEngagements.setSpacingAfter(10f);
            document.add(paragEngagements);

            PdfPTable tableDesc = new PdfPTable(pointColumnWidths);
            tableDesc.setSpacingAfter(30f);

            PdfPCell cellDesc = new PdfPCell(new Paragraph(internshipApplication.getInternshipOffer().getDescription()));
            cellDesc.setUseVariableBorders(true);
            cellDesc.setPadding(9);

            tableDesc.addCell(cellDesc);
            document.add(tableDesc);

            Paragraph paragResponsibilities = new Paragraph("RESPONSABILITES", mediumBold);
            paragResponsibilities.setAlignment(Element.ALIGN_CENTER);
            paragResponsibilities.setSpacingAfter(10f);
            document.add(paragResponsibilities);

            Paragraph paragEngagementTitleCollege = new Paragraph("Le Collège s’engage à :", mediumBold);
            paragEngagementTitleCollege.setAlignment(Element.ALIGN_LEFT);
            paragEngagementTitleCollege.setSpacingAfter(10f);
            document.add(paragEngagementTitleCollege);

            Paragraph paragEngagementCollege = new Paragraph(internship.getEngagements().get(COLLEGE_ENGAGEMENT_KEY), standard);
            paragEngagementCollege.setAlignment(Element.ALIGN_LEFT);
            paragEngagementCollege.setSpacingAfter(15f);
            document.add(paragEngagementCollege);

            Paragraph paragEngagementTitleEnterprise = new Paragraph("L’entreprise s’engage à :", mediumBold);
            paragEngagementTitleEnterprise.setAlignment(Element.ALIGN_LEFT);
            paragEngagementTitleEnterprise.setSpacingAfter(10f);
            document.add(paragEngagementTitleEnterprise);

            Paragraph paragEngagementEnterprise = new Paragraph(internship.getEngagements().get(ENTERPRISE_ENGAGEMENT_KEY), standard);
            paragEngagementEnterprise.setAlignment(Element.ALIGN_LEFT);
            paragEngagementEnterprise.setSpacingAfter(15f);
            document.add(paragEngagementEnterprise);

            Paragraph paragEngagementTitleStudent = new Paragraph("L’étudiant s’engage à :", mediumBold);
            paragEngagementTitleStudent.setAlignment(Element.ALIGN_LEFT);
            paragEngagementTitleStudent.setSpacingAfter(10f);
            document.add(paragEngagementTitleStudent);

            Paragraph paragEngagementStudent = new Paragraph(internship.getEngagements().get(STUDENT_ENGAGEMENT_KEY), standard);
            paragEngagementStudent.setAlignment(Element.ALIGN_LEFT);
            paragEngagementStudent.setSpacingAfter(30f);
            document.add(paragEngagementStudent);

            Paragraph paragSignature = new Paragraph("SIGNATURES", mediumBold);
            paragSignature.setAlignment(Element.ALIGN_CENTER);
            paragSignature.setSpacingAfter(20f);
            document.add(paragSignature);

            Paragraph paragEngagementContract = new Paragraph("Les parties s’engagent à respecter cette entente de stage", mediumBold);
            paragEngagementContract.setAlignment(Element.ALIGN_LEFT);
            paragEngagementContract.setSpacingAfter(20f);
            document.add(paragEngagementContract);

            Paragraph paragEngagementContractAll = new Paragraph("En foi de quoi les parties ont signé, ", mediumBold);
            paragEngagementContractAll.setAlignment(Element.ALIGN_LEFT);
            paragEngagementContractAll.setSpacingAfter(20f);
            document.add(paragEngagementContractAll);

            Paragraph paragEngagementContractStudent = new Paragraph("L’étudiant(e) :", mediumBold);
            paragEngagementContractStudent.setAlignment(Element.ALIGN_CENTER);
            paragEngagementContractStudent.setSpacingAfter(20f);
            document.add(paragEngagementContractStudent);

            float[] pointColumnWidthsSignatures = {150F, 150F};

            PdfPTable tableSignatureStudent = new PdfPTable(pointColumnWidthsSignatures);
            tableDesc.setSpacingAfter(30f);

            PdfPCell cellStudentSignature = new PdfPCell(new Paragraph("Signature Étudiant (à changer)"));
            cellStudentSignature.setUseVariableBorders(true);
            cellStudentSignature.setBorderWidthLeft(0f);
            cellStudentSignature.setBorderWidthRight(0f);
            cellStudentSignature.setBorderWidthTop(0f);
            cellStudentSignature.setPadding(3);
            tableSignatureStudent.addCell(cellStudentSignature);

            PdfPCell cellStudentDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
            cellStudentDateSignature.setUseVariableBorders(true);
            cellStudentDateSignature.setBorderWidthLeft(0f);
            cellStudentDateSignature.setBorderWidthRight(0f);
            cellStudentDateSignature.setBorderWidthTop(0f);
            cellStudentDateSignature.setPadding(3);
            tableSignatureStudent.addCell(cellStudentDateSignature);

            PdfPCell cellStudentNameSignature = new PdfPCell(new Paragraph(internshipApplication.getStudent().getFirstName() + " " + internshipApplication.getStudent().getLastName()));
            cellStudentNameSignature.setUseVariableBorders(true);
            cellStudentNameSignature.setBorderWidthLeft(0f);
            cellStudentNameSignature.setBorderWidthRight(0f);
            cellStudentNameSignature.setBorderWidthBottom(0f);
            cellStudentNameSignature.setPadding(3);
            tableSignatureStudent.addCell(cellStudentNameSignature);

            PdfPCell cellStudentDateTitleSignature = new PdfPCell(new Paragraph("Date"));
            cellStudentDateTitleSignature.setUseVariableBorders(true);
            cellStudentDateTitleSignature.setBorderWidthLeft(0f);
            cellStudentDateTitleSignature.setBorderWidthRight(0f);
            cellStudentDateTitleSignature.setBorderWidthBottom(0f);
            cellStudentDateTitleSignature.setPadding(3);
            tableSignatureStudent.addCell(cellStudentDateTitleSignature);

            document.add(tableSignatureStudent);

            Paragraph paragEngagementContractEnterprise = new Paragraph("L’employeur :", mediumBold);
            paragEngagementContractEnterprise.setAlignment(Element.ALIGN_CENTER);
            paragEngagementContractEnterprise.setSpacingAfter(20f);
            document.add(paragEngagementContractEnterprise);

            PdfPTable tableSignatureEnterprise = new PdfPTable(pointColumnWidthsSignatures);
            tableDesc.setSpacingAfter(30f);

            PdfPCell cellEnterpriseSignature = new PdfPCell(new Paragraph("Signature Entreprise (à changer)"));
            cellEnterpriseSignature.setUseVariableBorders(true);
            cellEnterpriseSignature.setBorderWidthLeft(0f);
            cellEnterpriseSignature.setBorderWidthRight(0f);
            cellEnterpriseSignature.setBorderWidthTop(0f);
            cellEnterpriseSignature.setPadding(3);
            tableSignatureEnterprise.addCell(cellEnterpriseSignature);

            PdfPCell cellEnterpriseDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
            cellEnterpriseDateSignature.setUseVariableBorders(true);
            cellEnterpriseDateSignature.setBorderWidthLeft(0f);
            cellEnterpriseDateSignature.setBorderWidthRight(0f);
            cellEnterpriseDateSignature.setBorderWidthTop(0f);
            cellEnterpriseDateSignature.setPadding(3);
            tableSignatureEnterprise.addCell(cellEnterpriseDateSignature);

            PdfPCell cellEnterpriseNameSignature = new PdfPCell(new Paragraph(internshipApplication.getInternshipOffer().getMonitor().getFirstName() + " " + internshipApplication.getInternshipOffer().getMonitor().getLastName()));
            cellEnterpriseNameSignature.setUseVariableBorders(true);
            cellEnterpriseNameSignature.setBorderWidthLeft(0f);
            cellEnterpriseNameSignature.setBorderWidthRight(0f);
            cellEnterpriseNameSignature.setBorderWidthBottom(0f);
            cellEnterpriseNameSignature.setPadding(3);
            tableSignatureEnterprise.addCell(cellEnterpriseNameSignature);

            PdfPCell cellEnterpriseDateTitleSignature = new PdfPCell(new Paragraph("Date"));
            cellEnterpriseDateTitleSignature.setUseVariableBorders(true);
            cellEnterpriseDateTitleSignature.setBorderWidthLeft(0f);
            cellEnterpriseDateTitleSignature.setBorderWidthRight(0f);
            cellEnterpriseDateTitleSignature.setBorderWidthBottom(0f);
            cellEnterpriseDateTitleSignature.setPadding(3);
            tableSignatureEnterprise.addCell(cellEnterpriseDateTitleSignature);

            document.add(tableSignatureEnterprise);

            Paragraph paragEngagementContractInternshipOwner = new Paragraph("Le gestionnaire de stage :", mediumBold);
            paragEngagementContractInternshipOwner.setAlignment(Element.ALIGN_CENTER);
            paragEngagementContractInternshipOwner.setSpacingAfter(20f);
            document.add(paragEngagementContractInternshipOwner);

            PdfPTable tableSignatureInternshipOwner = new PdfPTable(pointColumnWidthsSignatures);
            tableDesc.setSpacingAfter(30f);

            PdfPCell cellInternshipOwnerSignature = new PdfPCell(new Paragraph("Signature GS (à changer)"));
            cellInternshipOwnerSignature.setUseVariableBorders(true);
            cellInternshipOwnerSignature.setBorderWidthLeft(0f);
            cellInternshipOwnerSignature.setBorderWidthRight(0f);
            cellInternshipOwnerSignature.setBorderWidthTop(0f);
            cellInternshipOwnerSignature.setPadding(3);
            tableSignatureInternshipOwner.addCell(cellInternshipOwnerSignature);

            PdfPCell cellInternshipOwnerDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
            cellInternshipOwnerDateSignature.setUseVariableBorders(true);
            cellInternshipOwnerDateSignature.setBorderWidthLeft(0f);
            cellInternshipOwnerDateSignature.setBorderWidthRight(0f);
            cellInternshipOwnerDateSignature.setBorderWidthTop(0f);
            cellInternshipOwnerDateSignature.setPadding(3);
            tableSignatureInternshipOwner.addCell(cellInternshipOwnerDateSignature);

            PdfPCell cellInternshipOwnerNameSignature = new PdfPCell(new Paragraph(optionalInternshipManager.get().getFirstName() + " " + optionalInternshipManager.get().getLastName()));
            cellInternshipOwnerNameSignature.setUseVariableBorders(true);
            cellInternshipOwnerNameSignature.setBorderWidthLeft(0f);
            cellInternshipOwnerNameSignature.setBorderWidthRight(0f);
            cellInternshipOwnerNameSignature.setBorderWidthBottom(0f);
            cellInternshipOwnerNameSignature.setPadding(3);
            tableSignatureInternshipOwner.addCell(cellInternshipOwnerNameSignature);

            PdfPCell cellInternshipOwnerDateTitleSignature = new PdfPCell(new Paragraph("Date"));
            cellInternshipOwnerDateTitleSignature.setUseVariableBorders(true);
            cellInternshipOwnerDateTitleSignature.setBorderWidthLeft(0f);
            cellInternshipOwnerDateTitleSignature.setBorderWidthRight(0f);
            cellInternshipOwnerDateTitleSignature.setBorderWidthBottom(0f);
            cellInternshipOwnerDateTitleSignature.setPadding(3);
            tableSignatureInternshipOwner.addCell(cellInternshipOwnerDateTitleSignature);

            document.add(tableSignatureInternshipOwner);

            document.close();
            writer.close();

            pdfDocument.setName("Contract.pdf");
            pdfDocument.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return pdfDocument;
    }
}
