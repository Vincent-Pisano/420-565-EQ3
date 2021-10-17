package com.eq3.backend.generator;

import com.eq3.backend.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.*;
import static com.eq3.backend.utils.UtilsGenerator.*;

public class GenerateContract {

    public static ByteArrayOutputStream generatePdfContract(Internship internship, Optional<InternshipManager> optionalInternshipManager) throws Exception{
        InternshipManager internshipManager = optionalInternshipManager.get();
        InternshipApplication internshipApplication = internship.getInternshipApplication();
        InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
        Monitor monitor = internshipOffer.getMonitor();

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        generateTitlePage(document);

        document.newPage();

        Paragraph paragEmptySmall = new Paragraph(EMPTY);

        Paragraph paragAnd = new Paragraph(AND, MEDIUM_BOLD);
        paragAnd.setAlignment(Element.ALIGN_CENTER);
        paragAnd.setSpacingAfter(40f);

        Paragraph paragEntente = new Paragraph(AGREEMENT_BETWEEN, MEDIUM_BOLD);
        setUpParag(document, paragEntente, paragEntente, Element.ALIGN_CENTER, 50f);

        Paragraph paragCadre = new Paragraph(PARTICIPANTS, STANDARD);
        paragCadre.setAlignment(Element.ALIGN_CENTER);
        document.add(paragCadre);

        Paragraph paragCadreInternshipOwner = new Paragraph(INTERNSHIP_MANAGER + internshipManager.getFirstName() + EMPTY + internshipManager.getLastName() + ",", STANDARD);
        setUpParag(document, paragCadreInternshipOwner, paragCadreInternshipOwner, Element.ALIGN_CENTER, 40f);

        document.add(paragAnd);

        Paragraph paragCadreMonitor = new Paragraph(EMPLOYER + monitor.getFirstName() + EMPTY + monitor.getLastName() + ",", STANDARD);
        setUpParag(document, paragCadreMonitor, paragCadreMonitor, Element.ALIGN_CENTER, 40f);

        document.add(paragAnd);

        Paragraph paragCadreStudent = new Paragraph(STUDENT + internshipApplication.getStudent().getFirstName() + EMPTY + internshipApplication.getStudent().getLastName() + ",", STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreStudent, Element.ALIGN_CENTER, 40f);

        Paragraph paragCadreConditions = new Paragraph("Conviennent des conditions de stage suivantes :", STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreConditions, Element.ALIGN_CENTER, 10f);

        document.add(paragEmptySmall);

        float[] pointColumnWidths = {150F};
        PdfPTable table = new PdfPTable(pointColumnWidths);

        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Chunk c = new Chunk("ENDROIT DU STAGE", fontHeader);
        Paragraph paragraphHeader = new Paragraph(c);

        PdfPCell cell1 = new PdfPCell(paragraphHeader);
        setUpTitleCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Adresse : " + internshipOffer.getAddress() + ", " + internshipOffer.getCity() + ", " + internshipOffer.getPostalCode()));
        setUpCell(cell2);

        Chunk c2 = new Chunk("DURÉE DU STAGE", fontHeader);
        Paragraph paragraphDuration = new Paragraph(c2);
        PdfPCell cell3 = new PdfPCell(paragraphDuration);
        setUpTitleCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Paragraph(String.format("Date de début : " + internshipOffer.getStartDate(), DATE_FORMATTER)));
        setUpCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Paragraph(String.format("Date de fin : " + internshipOffer.getEndDate(), DATE_FORMATTER)));
        setUpCell(cell5);

        InternshipOffer.WorkShift Workshift = internshipOffer.getWorkShift();

        Duration diff = Duration.between(internshipOffer.getStartDate().toInstant(), internshipOffer.getEndDate().toInstant());
        long diffWeeks = diff.toDays() / 7;
        long diffDays = diff.toDays() % 7;

        PdfPCell cell6 = new PdfPCell(new Paragraph("Nombre de semaines : " + diffWeeks + (diffDays != 0 ? " (et " + diffDays + " jours)" : "")));
        setUpCell(cell6);

        Chunk c3 = new Chunk("HORAIRE DE TRAVAIL", fontHeader);
        Paragraph paragraphWorkshift = new Paragraph(c3);

        PdfPCell cell7 = new PdfPCell(paragraphWorkshift);
        setUpTitleCell(cell7);

        PdfPCell cell8 = new PdfPCell(new Paragraph("Type d'horaire: " + (Workshift == InternshipOffer.WorkShift.DAY ? "Jour" : Workshift == InternshipOffer.WorkShift.NIGHT ? "Nuit" : "Flexibe")));
        setUpCell(cell8);

        PdfPCell cell9 = new PdfPCell(new Paragraph("Nombre d'heures total par semaine: " + internshipOffer.getWeeklyWorkTime() + " heures"));
        setUpCell(cell9);

        Chunk c4 = new Chunk(SALARY, fontHeader);
        Paragraph paragraphSalary = new Paragraph(c4);

        PdfPCell cell10 = new PdfPCell(paragraphSalary);
        setUpTitleCell(cell10);

        PdfPCell cell11 = new PdfPCell(new Paragraph("Salaire horaire: " + String.format("%.2f",internshipOffer.getHourlySalary()) + "$"));
        setUpCell(cell11);

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

        Paragraph paragEngagements = new Paragraph("TACHES ET RESPONSABILITES DU STAGIAIRE", MEDIUM_BOLD);
        setUpParag(document, paragEngagements, paragEngagements, Element.ALIGN_CENTER, 10f);

        PdfPTable tableDesc = new PdfPTable(pointColumnWidths);
        tableDesc.setSpacingAfter(30f);

        PdfPCell cellDesc = new PdfPCell(new Paragraph(internshipOffer.getDescription()));
        cellDesc.setUseVariableBorders(true);
        cellDesc.setPadding(9);

        tableDesc.addCell(cellDesc);
        document.add(tableDesc);

        Paragraph paragResponsibilities = new Paragraph("RESPONSABILITES", MEDIUM_BOLD);
        setUpParag(document, paragResponsibilities, paragResponsibilities, Element.ALIGN_CENTER, 10f);

        Paragraph paragEngagementTitleCollege = new Paragraph("Le Collège s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleCollege, paragEngagementTitleCollege, Element.ALIGN_LEFT, 10f);

        Paragraph paragEngagementCollege = new Paragraph(internship.getEngagements().get(COLLEGE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementCollege, paragEngagementCollege, Element.ALIGN_LEFT, 15f);

        Paragraph paragEngagementTitleEnterprise = new Paragraph("L’entreprise s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleEnterprise, paragEngagementTitleEnterprise, Element.ALIGN_LEFT, 10f);

        Paragraph paragEngagementEnterprise = new Paragraph(internship.getEngagements().get(ENTERPRISE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementEnterprise, paragEngagementEnterprise, Element.ALIGN_LEFT, 15f);

        Paragraph paragEngagementTitleStudent = new Paragraph("L’étudiant s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleStudent, paragEngagementTitleStudent, Element.ALIGN_LEFT, 10f);

        Paragraph paragEngagementStudent = new Paragraph(internship.getEngagements().get(STUDENT_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementStudent, paragEngagementStudent, Element.ALIGN_LEFT, 30f);

        Paragraph paragSignature = new Paragraph("SIGNATURES", MEDIUM_BOLD);
        setUpParag(document, paragSignature, paragSignature, Element.ALIGN_CENTER, 20f);

        Paragraph paragEngagementContract = new Paragraph("Les parties s’engagent à respecter cette entente de stage", MEDIUM_BOLD);
        setUpParag(document, paragEngagementContract, paragEngagementContract, Element.ALIGN_LEFT, 20f);

        Paragraph paragEngagementContractAll = new Paragraph("En foi de quoi les parties ont signé, ", MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractAll, paragEngagementContractAll, Element.ALIGN_LEFT, 20f);

        Paragraph paragEngagementContractStudent = new Paragraph(STUDENT, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractStudent, paragEngagementContractStudent, Element.ALIGN_CENTER, 20f);

        float[] pointColumnWidthsSignatures = {150F, 150F};

        PdfPTable tableSignatureStudent = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(30f);

        PdfPCell cellStudentSignature = new PdfPCell(new Paragraph("Signature Étudiant (à changer)"));
        setUpTopSignatureCell(tableSignatureStudent, cellStudentSignature);

        PdfPCell cellStudentDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureStudent, cellStudentDateSignature);

        PdfPCell cellStudentNameSignature = new PdfPCell(new Paragraph(internshipApplication.getStudent().getFirstName() + EMPTY + internshipApplication.getStudent().getLastName()));
        setUpBottomSignatureCell(tableSignatureStudent, cellStudentNameSignature);

        PdfPCell cellStudentDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureStudent, cellStudentDateTitleSignature);

        document.add(tableSignatureStudent);

        Paragraph paragEngagementContractEnterprise = new Paragraph(EMPLOYER, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractEnterprise, paragEngagementContractEnterprise, Element.ALIGN_CENTER, 20f);

        PdfPTable tableSignatureEnterprise = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(30f);

        PdfPCell cellEnterpriseSignature = new PdfPCell(new Paragraph("Signature Entreprise (à changer)"));
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseSignature);

        PdfPCell cellEnterpriseDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseDateSignature);

        PdfPCell cellEnterpriseNameSignature = new PdfPCell(new Paragraph(monitor.getFirstName() + " " + monitor.getLastName()));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseNameSignature);

        PdfPCell cellEnterpriseDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseDateTitleSignature);

        document.add(tableSignatureEnterprise);

        Paragraph paragEngagementContractInternshipOwner = new Paragraph(INTERNSHIP_MANAGER, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractInternshipOwner, paragEngagementContractInternshipOwner, Element.ALIGN_CENTER, 20f);

        PdfPTable tableSignatureInternshipOwner = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(30f);

        PdfPCell cellInternshipOwnerSignature = new PdfPCell(new Paragraph("Signature GS (à changer)"));
        setUpTopSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerSignature);

        PdfPCell cellInternshipOwnerDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerDateSignature);

        PdfPCell cellInternshipOwnerNameSignature = new PdfPCell(new Paragraph(internshipManager.getFirstName() + " " + internshipManager.getLastName()));
        setUpBottomSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerNameSignature);

        PdfPCell cellInternshipOwnerDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerDateTitleSignature);

        document.add(tableSignatureInternshipOwner);

        document.close();
        writer.close();

        return baos;
    }

    private static void generateTitlePage(Document document) throws DocumentException {
        Paragraph paragEmpty = new Paragraph(EMPTY);
        paragEmpty.setSpacingAfter(325f);

        Paragraph paragTitle = new Paragraph(CONTRACT_TITLE, LARGE_BOLD);
        paragTitle.setSpacingAfter(350f);
        paragTitle.setAlignment(Element.ALIGN_CENTER);

        Paragraph paragDate = new Paragraph(LocalDate.now().format(DATE_FORMATTER));
        paragDate.setAlignment(Element.ALIGN_CENTER);

        document.add(paragEmpty);
        document.add(paragTitle);
        document.add(paragDate);
    }

    private static void setUpParag(Document document, Paragraph paragCadreStudent, Paragraph paragCadreConditions, int alignCenter, float spaceAfter) throws DocumentException {
        paragCadreConditions.setAlignment(alignCenter);
        paragCadreStudent.setSpacingAfter(spaceAfter);
        document.add(paragCadreConditions);
    }

    private static void setUpBottomSignatureCell(PdfPTable tableSignatureStudent, PdfPCell bottomCell) {
        bottomCell.setUseVariableBorders(true);
        bottomCell.setBorderWidthLeft(0f);
        bottomCell.setBorderWidthRight(0f);
        bottomCell.setBorderWidthBottom(0f);
        bottomCell.setPadding(3);
        tableSignatureStudent.addCell(bottomCell);
    }

    private static void setUpTopSignatureCell(PdfPTable tableSignatureStudent, PdfPCell topCell) {
        topCell.setUseVariableBorders(true);
        topCell.setBorderWidthLeft(0f);
        topCell.setBorderWidthRight(0f);
        topCell.setBorderWidthTop(0f);
        topCell.setPadding(3);
        tableSignatureStudent.addCell(topCell);
    }

    private static void setUpCell(PdfPCell cell) {
        cell.setUseVariableBorders(true);
        cell.setBorderWidthTop(0f);
        cell.setPadding(7);
    }

    private static void setUpTitleCell(PdfPCell cell) {
        cell.setBackgroundColor(BG_COLOR);
        cell.setUseVariableBorders(true);
        cell.setBorderWidthBottom(0f);
        cell.setPadding(7);
    }

}
