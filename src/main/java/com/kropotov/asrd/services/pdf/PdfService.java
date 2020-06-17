package com.kropotov.asrd.services.pdf;

import org.springframework.stereotype.Service;

@Service
public interface PdfService {
    void generatePlainPdf(String pathAndFileName, String text);

}
