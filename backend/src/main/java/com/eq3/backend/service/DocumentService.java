package com.eq3.backend.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    public void updateDocument(String input, String output)
            throws IOException {

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(input)))
        ) {

            List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
            //Iterate over paragraph list and check for the replaceable text in each paragraph
            for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
                System.out.println(xwpfParagraph.getRuns());
                System.out.println("-----------");
                for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
                    String docText = xwpfRun.getText(0);
                    if (docText != null) {
                        //replacement and setting position
                        docText = docText.replace("GS_NAME", "San Pellegrino");
                        xwpfRun.setText(docText, 0);
                    }
                }
            }

            // save the docs
            try (FileOutputStream out = new FileOutputStream(output)) {
                doc.write(out);
            }

        }

    }

}
