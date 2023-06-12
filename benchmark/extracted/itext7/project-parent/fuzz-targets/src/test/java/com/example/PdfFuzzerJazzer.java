package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.exceptions.*;

public class PdfFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            InputStream stream = new ByteArrayInputStream(data.consumeRemainingAsString().getBytes(StandardCharsets.UTF_8));
            PdfReader reader = new PdfReader(stream);
            PdfDocument pdfDoc = new PdfDocument(reader);
        } catch (java.io.IOException | com.itextpdf.io.exceptions.IOException | com.itextpdf.kernel.exceptions.PdfException | java.lang.AssertionError | java.lang.ClassCastException | java.lang.StringIndexOutOfBoundsException e) {
        }
    }
}
