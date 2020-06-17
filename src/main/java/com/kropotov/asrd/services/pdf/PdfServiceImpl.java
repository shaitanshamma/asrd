package com.kropotov.asrd.services.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kropotov.asrd.exceptions.EmailException;
import com.kropotov.asrd.exceptions.PdfWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService {

    @Override
    public void generatePlainPdf(String pathAndFileName, String text) {
        writeFile(new Document(), pathAndFileName, text);
    }

    private void writeFile(Document document, String pathAndFileName, String text) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream((pathAndFileName)));
        } catch (DocumentException | FileNotFoundException ex) {
            log.error("Ошибка получения экземпляра документа {}.", pathAndFileName);
            throw new PdfWriteException("Ошибка получения экземпляра документа " +  pathAndFileName, ex);
        }
        document.open();
        try {
            document.add(new Paragraph(text));
        } catch (DocumentException ex) {
            log.error("Ошибка при модификации документа {}.", pathAndFileName);
            throw new PdfWriteException("Ошибка при модификации документа " + pathAndFileName, ex);
        }
        document.close();
    }
}
