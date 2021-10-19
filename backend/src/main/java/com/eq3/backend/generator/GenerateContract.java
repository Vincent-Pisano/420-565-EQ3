package com.eq3.backend.generator;

import com.eq3.backend.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

        Paragraph paragAgreement = new Paragraph(AGREEMENT_BETWEEN, MEDIUM_BOLD);
        setUpParag(document, paragAgreement, paragAgreement, Element.ALIGN_CENTER, LARGE_SPACE);

        //PARTICIPANTS
        Paragraph paragCadre = new Paragraph(PARTICIPANTS, STANDARD);
        paragCadre.setAlignment(Element.ALIGN_CENTER);
        document.add(paragCadre);

        Paragraph paragCadreInternshipManager = new Paragraph(INTERNSHIP_MANAGER + internshipManager.getFirstName() +
                EMPTY + internshipManager.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreInternshipManager, paragCadreInternshipManager, Element.ALIGN_CENTER, MEDIUM_SPACE);

        document.add(paragAnd);

        Paragraph paragCadreMonitor = new Paragraph(MONITOR + monitor.getFirstName() + EMPTY +
                monitor.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreMonitor, paragCadreMonitor, Element.ALIGN_CENTER, MEDIUM_SPACE);

        document.add(paragAnd);

        Paragraph paragCadreStudent = new Paragraph(STUDENT + student.getFirstName() +
                EMPTY + student.getLastName() + COMA, STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreStudent, Element.ALIGN_CENTER, MEDIUM_SPACE);
        //END PARTICIPANTS

        Paragraph paragCadreConditions = new Paragraph(TERMS_OF_CONDITION, STANDARD);
        setUpParag(document, paragCadreStudent, paragCadreConditions, Element.ALIGN_CENTER, TINY_SPACE);

        document.add(paragEmptySmall);

        //TABLE DETAILS INTERNSHIP
        PdfPTable tableDetailsInternship = new PdfPTable(pointColumnWidths);

        Chunk chunkPlace = new Chunk(PLACE, MEDIUM_BOLD);
        PdfPCell cellPlaceHeader = new PdfPCell(new Paragraph(chunkPlace));
        setUpTitleCell(cellPlaceHeader);
        tableDetailsInternship.addCell(cellPlaceHeader);

        PdfPCell cellPlace = new PdfPCell(new Paragraph(ADDRESS + internshipOffer.getAddress() + COMA + EMPTY +
                internshipOffer.getCity() + COMA + EMPTY + internshipOffer.getPostalCode()));
        setUpCell(cellPlace);
        tableDetailsInternship.addCell(cellPlace);

        Chunk chunkDuration = new Chunk(DURATION, MEDIUM_BOLD);
        PdfPCell cellDurationHeader = new PdfPCell(new Paragraph(chunkDuration));
        setUpTitleCell(cellDurationHeader);
        tableDetailsInternship.addCell(cellDurationHeader);

        String dateStartFormatted = FORMATTER_START_END.format(internshipOffer.getStartDate());
        PdfPCell cellStartDate = new PdfPCell(new Paragraph(START_DATE + dateStartFormatted));
        setUpCell(cellStartDate);
        tableDetailsInternship.addCell(cellStartDate);

        String dateEndFormatted = FORMATTER_START_END.format(internshipOffer.getEndDate());
        PdfPCell cellEndDate = new PdfPCell(new Paragraph(END_DATE + dateEndFormatted));
        setUpCell(cellEndDate);
        tableDetailsInternship.addCell(cellEndDate);

        Duration diff = Duration.between(internshipOffer.getStartDate().toInstant(), internshipOffer.getEndDate().toInstant());
        long diffWeeks = diff.toDays() / 7;
        long diffDays = diff.toDays() % 7;
        PdfPCell cellDuration = new PdfPCell(new Paragraph(NUMBER_WEEKS + diffWeeks + (diffDays != 0 ?
                AND_DAY + diffDays + END_DAY : NOTHING)));
        setUpCell(cellDuration);
        tableDetailsInternship.addCell(cellDuration);

        Chunk chunkWorkshiftHeader = new Chunk(WORK_HOURS, MEDIUM_BOLD);
        PdfPCell cellWorkshiftHeader = new PdfPCell(new Paragraph(chunkWorkshiftHeader));
        setUpTitleCell(cellWorkshiftHeader);
        tableDetailsInternship.addCell(cellWorkshiftHeader);

        PdfPCell cellWorkshiftType = new PdfPCell(new Paragraph(WORKSHIFT_TYPE + (workShift == InternshipOffer.WorkShift.DAY ?
                DAY : workShift == InternshipOffer.WorkShift.NIGHT ? NIGHT : FLEXIBLE)));
        setUpCell(cellWorkshiftType);
        tableDetailsInternship.addCell(cellWorkshiftType);

        PdfPCell cellWorkshiftHours = new PdfPCell(new Paragraph(TOTAL_HOURS + internshipOffer.getWeeklyWorkTime() + HOURS));
        setUpCell(cellWorkshiftHours);
        tableDetailsInternship.addCell(cellWorkshiftHours);

        Chunk chunkSalaryHeader = new Chunk(SALARY, MEDIUM_BOLD);
        PdfPCell cellSalaryHeader = new PdfPCell(new Paragraph(chunkSalaryHeader));
        setUpTitleCell(cellSalaryHeader);
        tableDetailsInternship.addCell(cellSalaryHeader);

        PdfPCell cellSalaryHourly = new PdfPCell(new Paragraph(SALARY_HOURS + String.format(DOUBLE_FORMAT, internshipOffer.getHourlySalary()) + DOLLAR));
        setUpCell(cellSalaryHourly);
        tableDetailsInternship.addCell(cellSalaryHourly);

        document.add(tableDetailsInternship);
        document.newPage();
        //END TABLE DETAILS INTERNSHIP

        Paragraph paragEngagements = new Paragraph(TASKS_RESPONSIBILITIES, MEDIUM_BOLD);
        setUpParag(document, paragEngagements, paragEngagements, Element.ALIGN_CENTER, TINY_SPACE);

        //TABLE TASKS RESPONSIBILITIES
        PdfPTable tableTasksResponsibilities = new PdfPTable(pointColumnWidths);
        tableTasksResponsibilities.setSpacingAfter(BELOW_MEDIUM_SPACE);

        PdfPCell cellDesc = new PdfPCell(new Paragraph(internshipOffer.getDescription()));
        cellDesc.setUseVariableBorders(true);
        cellDesc.setPadding(9);

        tableTasksResponsibilities.addCell(cellDesc);
        document.add(tableTasksResponsibilities);
        //END TABLE TASKS RESPONSIBILITIES

        //RESPONSIBILITIES
        Paragraph paragResponsibilities = new Paragraph(RESPONSIBILITIES, MEDIUM_BOLD);
        setUpParag(document, paragResponsibilities, paragResponsibilities, Element.ALIGN_CENTER, TINY_SPACE);

        Paragraph paragEngagementTitleCollege = new Paragraph(COLLEGE_COMMITSMENTS, MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleCollege, paragEngagementTitleCollege, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementCollege = new Paragraph(internship.getEngagements().get(COLLEGE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementCollege, paragEngagementCollege, Element.ALIGN_LEFT, SMALLER_SPACE);

        Paragraph paragEngagementTitleEnterprise = new Paragraph(ENTERPRISE_COMMITSMENTS, MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleEnterprise, paragEngagementTitleEnterprise, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementEnterprise = new Paragraph(internship.getEngagements().get(ENTERPRISE_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementEnterprise, paragEngagementEnterprise, Element.ALIGN_LEFT, SMALLER_SPACE);

        Paragraph paragEngagementTitleStudent = new Paragraph(STUDENT_COMMITSMENTS, MEDIUM_BOLD);
        setUpParag(document, paragEngagementTitleStudent, paragEngagementTitleStudent, Element.ALIGN_LEFT, TINY_SPACE);

        Paragraph paragEngagementStudent = new Paragraph(internship.getEngagements().get(STUDENT_ENGAGEMENT_KEY), STANDARD);
        setUpParag(document, paragEngagementStudent, paragEngagementStudent, Element.ALIGN_LEFT, BELOW_MEDIUM_SPACE);

        document.newPage();

        Paragraph paragSignature = new Paragraph(SIGNATURES, MEDIUM_BOLD);
        setUpParag(document, paragSignature, paragSignature, Element.ALIGN_CENTER, SMALL_SPACE);

        Paragraph paragEngagementContract = new Paragraph(CONTRACT_CONDITIONS, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContract, paragEngagementContract, Element.ALIGN_LEFT, SMALL_SPACE);

        Paragraph paragEngagementContractAll = new Paragraph(ONCE_SIGNED, MEDIUM_BOLD);
        setUpParag(document, paragEngagementContractAll, paragEngagementContractAll, Element.ALIGN_LEFT, SMALL_SPACE);

        document.close();
        writer.close();

        return baos;
    }

    public static ByteArrayOutputStream signPdfContract(Monitor monitor, byte[] contrat) throws DocumentException, IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(contrat);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(bais);
        PdfStamper stamper = new PdfStamper(reader, baos);

        PdfContentByte content = stamper.getUnderContent(reader.getNumberOfPages());
        generateSignatureMonitor(monitor, content);

        stamper.close();
        reader.close();

        return baos;
    }

    private static void generateSignatureMonitor(Monitor monitor, PdfContentByte content) throws DocumentException, IOException {
        ColumnText ct = new ColumnText(content);
        ct.setSimpleColumn(new Rectangle(WIDTH_SIGNATURE_TITLE, HEIGHT_SIGNATURE_TITLE, X_SIGNATURE_TITLE, Y_SIGNATURE_TITLE_MONITOR));
        ct.addElement(new Paragraph(MONITOR, MEDIUM_BOLD));
        ct.go();

        PdfPTable pdfPTable = generateTableSignature(monitor);
        pdfPTable.setTotalWidth(WIDTH_SIGNATURE_TABLE);
        pdfPTable.writeSelectedRows(0, -1, X_SIGNATURE_TABLE, Y_SIGNATURE_TABLE_MONITOR, content);
    }

    public static ByteArrayOutputStream signPdfContract(InternshipManager internshipManager, byte[] contrat) throws DocumentException, IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(contrat);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(bais);
        PdfStamper stamper = new PdfStamper(reader, baos);

        PdfContentByte content = stamper.getUnderContent(reader.getNumberOfPages());
        generateSignatureInternshipManager(internshipManager, content);

        stamper.close();
        reader.close();

        return baos;
    }

    private static void generateSignatureInternshipManager(InternshipManager internshipManager, PdfContentByte content) throws DocumentException, IOException {
        ColumnText ct = new ColumnText(content);
        ct.setSimpleColumn(new Rectangle(WIDTH_SIGNATURE_TITLE, HEIGHT_SIGNATURE_TITLE, X_SIGNATURE_TITLE, Y_SIGNATURE_TITLE_INTERNSHIP_MANAGER));
        ct.addElement(new Paragraph(INTERNSHIP_MANAGER, MEDIUM_BOLD));
        ct.go();

        PdfPTable pdfPTable = generateTableSignature(internshipManager);
        pdfPTable.setTotalWidth(WIDTH_SIGNATURE_TABLE);
        pdfPTable.writeSelectedRows(0, -1, X_SIGNATURE_TABLE, Y_SIGNATURE_TABLE_INTERNSHIP_MANAGER, content);
    }

    private static PdfPTable generateTableSignature(User user) throws IOException, BadElementException {
        float[] pointColumnWidthsSignatures = {WIDTH, WIDTH};

        //TABLE SIGNATURE ENTERPRISE
        PdfPTable tableSignatureEnterprise = new PdfPTable(pointColumnWidthsSignatures);
        tableSignatureEnterprise.setSpacingAfter(BELOW_MEDIUM_SPACE);

        Image img = Image.getInstance(user.getSignature().getData());
        img.scaleAbsoluteWidth(WIDTH_SIGNATURE_IMAGE);
        img.scaleAbsoluteHeight(HEIGHT_SIGNATURE_IMAGE);

        PdfPCell cellEnterpriseSignature = new PdfPCell(img);
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseSignature);

        Paragraph paragDate = new Paragraph(LocalDate.now().format(DATE_FORMATTER));
        paragDate.setAlignment(Element.ALIGN_BOTTOM);

        PdfPCell cellEnterpriseDateSignature = new PdfPCell(paragDate);
        cellEnterpriseDateSignature.setVerticalAlignment(Element.ALIGN_BOTTOM);
        setUpTopSignatureCell(tableSignatureEnterprise, cellEnterpriseDateSignature);

        PdfPCell cellEnterpriseNameSignature = new PdfPCell(new Paragraph(user.getFirstName() + EMPTY + user.getLastName()));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseNameSignature);

        PdfPCell cellEnterpriseDateTitleSignature = new PdfPCell(new Paragraph(DATE));
        setUpBottomSignatureCell(tableSignatureEnterprise, cellEnterpriseDateTitleSignature);
        return tableSignatureEnterprise;
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

    private static void setUpParag(Document document, Paragraph paragraph, Paragraph paragraphCenter,
                                   int alignCenter, float spaceAfter) throws DocumentException {
        paragraphCenter.setAlignment(alignCenter);
        paragraph.setSpacingAfter(spaceAfter);
        document.add(paragraphCenter);
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
