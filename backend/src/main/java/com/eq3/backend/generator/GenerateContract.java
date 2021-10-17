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
        Student student = internshipApplication.getStudent();
        Monitor monitor = internshipOffer.getMonitor();
        InternshipOffer.WorkShift workShift = internshipOffer.getWorkShift();

        float[] pointColumnWidths = {WIDTH};
        float[] pointColumnWidthsSignatures = {WIDTH, WIDTH};

        Paragraph paragEmptySmall = new Paragraph(EMPTY);
        Paragraph paragAnd = new Paragraph(AND, MEDIUM_BOLD);
        paragAnd.setAlignment(Element.ALIGN_CENTER);
        paragAnd.setSpacingAfter(MEDIUM_SPACE);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        generateTitlePage(document);
        document.newPage();

        Paragraph paragEntente = new Paragraph(AGREEMENT_BETWEEN, MEDIUM_BOLD);
        setUpParag(document, paragEntente, paragEntente, Element.ALIGN_CENTER, LARGE_SPACE);

        Paragraph paragCadre = new Paragraph(PARTICIPANTS, STANDARD);
        paragCadre.setAlignment(Element.ALIGN_CENTER);
        document.add(paragCadre);

        Paragraph paragCadreInternshipOwner = new Paragraph(INTERNSHIP_MANAGER + internshipManager.getFirstName() +
                EMPTY + internshipManager.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreInternshipOwner, paragCadreInternshipOwner, Element.ALIGN_CENTER, MEDIUM_SPACE);

        document.add(paragAnd);

        Paragraph paragCadreMonitor = new Paragraph(EMPLOYER + monitor.getFirstName() + EMPTY +
                monitor.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreMonitor, paragCadreMonitor, Element.ALIGN_CENTER, MEDIUM_SPACE);

        document.add(paragAnd);

        Paragraph paragCadreStudent = new Paragraph(STUDENT + student.getFirstName() +
                EMPTY + student.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreStudent, Element.ALIGN_CENTER, MEDIUM_SPACE);

        Paragraph paragCadreConditions = new Paragraph(TERMS_OF_CONDITION, STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreConditions, Element.ALIGN_CENTER, TINY_SPACE);

        document.add(paragEmptySmall);

        PdfPTable table = new PdfPTable(pointColumnWidths);

        Chunk c = new Chunk(PLACE, MEDIUM_BOLD);
        PdfPCell cell1 = new PdfPCell(new Paragraph(c));
        setUpTitleCell(cell1);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Adresse : " + internshipOffer.getAddress() + COMA + EMPTY +
                internshipOffer.getCity() + COMA + EMPTY + internshipOffer.getPostalCode()));
        setUpCell(cell2);
        table.addCell(cell2);

        Chunk c2 = new Chunk(DURATION, MEDIUM_BOLD);
        PdfPCell cell3 = new PdfPCell(new Paragraph(c2));
        setUpTitleCell(cell3);
        table.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Paragraph(String.format("Date de début : " + internshipOffer.getStartDate(), DATE_FORMATTER)));
        setUpCell(cell4);
        table.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Paragraph(String.format("Date de fin : " + internshipOffer.getEndDate(), DATE_FORMATTER)));
        setUpCell(cell5);
        table.addCell(cell5);

        Duration diff = Duration.between(internshipOffer.getStartDate().toInstant(), internshipOffer.getEndDate().toInstant());
        long diffWeeks = diff.toDays() / 7;
        long diffDays = diff.toDays() % 7;
        PdfPCell cell6 = new PdfPCell(new Paragraph("Nombre de semaines : " + diffWeeks + (diffDays != 0 ? " (et " + diffDays + " jours)" : "")));
        setUpCell(cell6);
        table.addCell(cell6);

        Chunk c3 = new Chunk("HORAIRE DE TRAVAIL", MEDIUM_BOLD);
        Paragraph paragraphWorkshift = new Paragraph(c3);

        PdfPCell cell7 = new PdfPCell(paragraphWorkshift);
        setUpTitleCell(cell7);
        table.addCell(cell7);

        PdfPCell cell8 = new PdfPCell(new Paragraph("Type d'horaire: " + (workShift == InternshipOffer.WorkShift.DAY ? "Jour" : workShift == InternshipOffer.WorkShift.NIGHT ? "Nuit" : "Flexibe")));
        setUpCell(cell8);
        table.addCell(cell8);

        PdfPCell cell9 = new PdfPCell(new Paragraph("Nombre d'heures total par semaine: " + internshipOffer.getWeeklyWorkTime() + " heures"));
        setUpCell(cell9);
        table.addCell(cell9);

        Chunk c4 = new Chunk(SALARY, MEDIUM_BOLD);
        PdfPCell cell10 = new PdfPCell(new Paragraph(c4));
        setUpTitleCell(cell10);
        table.addCell(cell10);

        PdfPCell cell11 = new PdfPCell(new Paragraph("Salaire horaire: " + String.format("%.2f",internshipOffer.getHourlySalary()) + "$"));
        setUpCell(cell11);
        table.addCell(cell11);

        document.add(table);
        document.newPage();

        Paragraph paragEngagements = new Paragraph("TACHES ET RESPONSABILITES DU STAGIAIRE", MEDIUM_BOLD);
        setUpParag(document, paragEngagements, paragEngagements, Element.ALIGN_CENTER, TINY_SPACE);

        PdfPTable tableDesc = new PdfPTable(pointColumnWidths);
        tableDesc.setSpacingAfter(BELOW_MEDIUM_SPACE);

        PdfPCell cellDesc = new PdfPCell(new Paragraph(internshipOffer.getDescription()));
        cellDesc.setUseVariableBorders(true);
        cellDesc.setPadding(9);

        tableDesc.addCell(cellDesc);
        document.add(tableDesc);

        Paragraph paragResponsibilities = new Paragraph("RESPONSABILITES", MEDIUM_BOLD);
        setUpParag(document, paragResponsibilities, paragResponsibilities, Element.ALIGN_CENTER, TINY_SPACE);

        Paragraph paragEngagementTitleCollege = new Paragraph("Le Collège s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleCollege, paragEngagementTitleCollege, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementCollege = new Paragraph(internship.getEngagements().get(COLLEGE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementCollege, paragEngagementCollege, Element.ALIGN_LEFT, SMALLER_SPACE);

        Paragraph paragEngagementTitleEnterprise = new Paragraph("L’entreprise s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleEnterprise, paragEngagementTitleEnterprise, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementEnterprise = new Paragraph(internship.getEngagements().get(ENTERPRISE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementEnterprise, paragEngagementEnterprise, Element.ALIGN_LEFT, SMALLER_SPACE);

        Paragraph paragEngagementTitleStudent = new Paragraph("L’étudiant s’engage à :", MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleStudent, paragEngagementTitleStudent, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementStudent = new Paragraph(internship.getEngagements().get(STUDENT_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementStudent, paragEngagementStudent, Element.ALIGN_LEFT, BELOW_MEDIUM_SPACE);

        Paragraph paragSignature = new Paragraph("SIGNATURES", MEDIUM_BOLD);
        setUpParag(document, paragSignature, paragSignature, Element.ALIGN_CENTER, SMALL_SPACE);

        Paragraph paragEngagementContract = new Paragraph("Les parties s’engagent à respecter cette entente de stage", MEDIUM_BOLD);
        setUpParag(document, paragEngagementContract, paragEngagementContract, Element.ALIGN_LEFT, SMALL_SPACE);

        Paragraph paragEngagementContractAll = new Paragraph("En foi de quoi les parties ont signé, ", MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractAll, paragEngagementContractAll, Element.ALIGN_LEFT, SMALL_SPACE);

        Paragraph paragEngagementContractStudent = new Paragraph(STUDENT, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractStudent, paragEngagementContractStudent, Element.ALIGN_CENTER, SMALL_SPACE);

        PdfPTable tableSignatureStudent = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(BELOW_MEDIUM_SPACE);

        PdfPCell cellStudentSignature = new PdfPCell(new Paragraph("Signature Étudiant (à changer)"));
        setUpTopSignatureCell(tableSignatureStudent, cellStudentSignature);

        PdfPCell cellStudentDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureStudent, cellStudentDateSignature);

        PdfPCell cellStudentNameSignature = new PdfPCell(new Paragraph(student.getFirstName() + EMPTY + student.getLastName()));
        setUpBottomSignatureCell(tableSignatureStudent, cellStudentNameSignature);

        PdfPCell cellStudentDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureStudent, cellStudentDateTitleSignature);

        document.add(tableSignatureStudent);

        Paragraph paragEngagementContractEnterprise = new Paragraph(EMPLOYER, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractEnterprise, paragEngagementContractEnterprise, Element.ALIGN_CENTER, SMALL_SPACE);

        PdfPTable tableSignatureEnterprise = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(BELOW_MEDIUM_SPACE);

        PdfPCell cellEnterpriseSignature = new PdfPCell(new Paragraph("Signature Entreprise (à changer)"));
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseSignature);

        PdfPCell cellEnterpriseDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseDateSignature);

        PdfPCell cellEnterpriseNameSignature = new PdfPCell(new Paragraph(monitor.getFirstName() + EMPTY + monitor.getLastName()));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseNameSignature);

        PdfPCell cellEnterpriseDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseDateTitleSignature);

        document.add(tableSignatureEnterprise);

        Paragraph paragEngagementContractInternshipOwner = new Paragraph(INTERNSHIP_MANAGER, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractInternshipOwner, paragEngagementContractInternshipOwner, Element.ALIGN_CENTER, SMALL_SPACE);

        PdfPTable tableSignatureInternshipOwner = new PdfPTable(pointColumnWidthsSignatures);
        tableDesc.setSpacingAfter(BELOW_MEDIUM_SPACE);

        PdfPCell cellInternshipOwnerSignature = new PdfPCell(new Paragraph("Signature GS (à changer)"));
        setUpTopSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerSignature);

        PdfPCell cellInternshipOwnerDateSignature = new PdfPCell(new Paragraph("Date Signature (à changer)"));
        setUpTopSignatureCell(tableSignatureInternshipOwner, cellInternshipOwnerDateSignature);

        PdfPCell cellInternshipOwnerNameSignature = new PdfPCell(new Paragraph(internshipManager.getFirstName() + EMPTY + internshipManager.getLastName()));
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
        paragEmpty.setSpacingAfter(START_SPACE);
        document.add(paragEmpty);

        Paragraph paragTitle = new Paragraph(CONTRACT_TITLE, LARGE_BOLD);
        paragTitle.setSpacingAfter(END_SPACE);
        paragTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(paragTitle);

        Paragraph paragDate = new Paragraph(LocalDate.now().format(DATE_FORMATTER));
        paragDate.setAlignment(Element.ALIGN_CENTER);
        document.add(paragDate);
    }

    private static void setUpParag(Document document, Paragraph paragCadreStudent, Paragraph paragCadreConditions, int alignCenter, float spaceAfter) throws DocumentException {
        paragCadreConditions.setAlignment(alignCenter);
        paragCadreStudent.setSpacingAfter(spaceAfter);
        document.add(paragCadreConditions);
    }

    private static void setUpBottomSignatureCell(PdfPTable tableSignature, PdfPCell bottomCell) {
        bottomCell.setUseVariableBorders(true);
        bottomCell.setBorderWidthLeft(NO_SPACE);
        bottomCell.setBorderWidthRight(NO_SPACE);
        bottomCell.setBorderWidthBottom(NO_SPACE);
        bottomCell.setPadding(PADDING_TABLE_SIGNATURES);
        tableSignature.addCell(bottomCell);
    }

    private static void setUpTopSignatureCell(PdfPTable tableSignature, PdfPCell topCell) {
        topCell.setUseVariableBorders(true);
        topCell.setBorderWidthLeft(NO_SPACE);
        topCell.setBorderWidthRight(NO_SPACE);
        topCell.setBorderWidthTop(NO_SPACE);
        topCell.setPadding(PADDING_TABLE_SIGNATURES);
        tableSignature.addCell(topCell);
    }

    private static void setUpCell(PdfPCell cell) {
        cell.setUseVariableBorders(true);
        cell.setBorderWidthTop(NO_SPACE);
        cell.setPadding(PADDING_TABLE_CONDITIONS);
    }

    private static void setUpTitleCell(PdfPCell cell) {
        cell.setBackgroundColor(BG_COLOR);
        cell.setUseVariableBorders(true);
        cell.setBorderWidthBottom(NO_SPACE);
        cell.setPadding(PADDING_TABLE_CONDITIONS);
    }
}
