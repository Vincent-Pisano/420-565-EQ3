package com.eq3.backend.service;

import com.eq3.backend.model.Internship;
import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.repository.InternshipRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.eq3.backend.utils.Utils.INTERNSHIP_CONTRACT_PATH;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private InternshipRepository internshipRepository;

    public DocumentService(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    public void saveInternship(InternshipApplication internshipApplication) throws IOException {

        try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(INTERNSHIP_CONTRACT_PATH)))) {

            List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
            //Iterate over paragraph list and check for the replaceable text in each paragraph
            for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
                for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
                    String docText = xwpfRun.getText(0);
                    if (docText != null) {
                        //replacement and setting position
                        docText = docText.replace("INTERNSHIP_MANAGER_NAME", "San Pellegrino");
                        xwpfRun.setText(docText, 0);
                    }
                }
            }

            Internship internship = new Internship();

            // save the docs
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                doc.write(out);
                out.close();
                doc.close();
                PDFDocument contract = new PDFDocument();
                contract.setName("contrat_de_stage.docx");
                contract.setContent(new Binary(BsonBinarySubType.BINARY, out.toByteArray()));


                internship.setInternshipContract(contract);
                internship.setInternshipApplication(internshipApplication);

                internshipRepository.save(internship);
            }

            // to prove that the byte array really contains the XWPFDocument
            /*try (FileOutputStream stream = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\contratOutput.docx")) {
                stream.write(internship.getInternshipContract().getContent().getData());
            }*/
        }

    }

}
