package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.exceptions.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PdfFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            InputStream stream = new ByteArrayInputStream(SMFData.getBytes(StandardCharsets.UTF_8));
            PdfReader reader = new PdfReader(stream);
            PdfDocument pdfDoc = new PdfDocument(reader);
        } catch (java.io.IOException | com.itextpdf.io.exceptions.IOException | com.itextpdf.kernel.exceptions.PdfException | java.lang.AssertionError | java.lang.ClassCastException | java.lang.StringIndexOutOfBoundsException e) {
        }
    }

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
