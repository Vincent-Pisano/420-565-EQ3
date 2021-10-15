package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.pdf.PdfWriter;

@Service
public class InternshipService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRepository internshipRepository;

    InternshipService(StudentRepository studentRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipApplicationRepository internshipApplicationRepository,
                   InternshipRepository internshipRepository
    ) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
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

    public Optional<Internship> saveInternship(InternshipApplication internshipApplication) {
        Internship internship = new Internship();
        internship.setInternshipApplication(internshipApplication);
        internship.setInternshipContract(getContract(internshipApplication));
        return Optional.of(internshipRepository.save(internship));
    }

    private PDFDocument getContract(InternshipApplication internshipApplication) {
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

            Paragraph paragCadreInternshipOwner = new Paragraph("Le gestionnaire de stage, ${GS_name},", standard);
            paragCadreInternshipOwner.setAlignment(Element.ALIGN_CENTER);
            paragCadreInternshipOwner.setSpacingAfter(40f);
            document.add(paragCadreInternshipOwner);

            Paragraph paragAnd = new Paragraph("et", mediumBold);
            paragAnd.setAlignment(Element.ALIGN_CENTER);
            paragAnd.setSpacingAfter(40f);
            document.add(paragAnd);

            Paragraph paragCadreMonitor = new Paragraph("L’employeur, ${monitor_name},", standard);
            paragCadreMonitor.setAlignment(Element.ALIGN_CENTER);
            paragCadreMonitor.setSpacingAfter(40f);
            document.add(paragCadreMonitor);

            document.add(paragAnd);

            Paragraph paragCadreStudent = new Paragraph("L’étudiant(e), ${student_name},", standard);
            paragCadreStudent.setAlignment(Element.ALIGN_CENTER);
            paragCadreStudent.setSpacingAfter(40f);
            document.add(paragCadreStudent);

            Paragraph paragCadreConditions = new Paragraph("Conviennent des conditions de stage suivantes :", standard);
            paragCadreConditions.setAlignment(Element.ALIGN_CENTER);
            document.add(paragCadreConditions);

            float [] pointColumnWidths = {150F};
            PdfPTable table = new PdfPTable(pointColumnWidths);

            // Adding cells to the table
            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);

            document.add(table);

            document.newPage();
            //Ici met les trucs de ta page, pas besoin de faire la partie signature, c'est Jules et Mathis qui vont la faire
            document.add(paragEntente);


            document.close();
            writer.close();

            pdfDocument.setName("Contract.pdf");
            pdfDocument.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return pdfDocument;
    }


}
