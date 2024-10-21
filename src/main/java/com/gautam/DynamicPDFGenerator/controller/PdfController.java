package com.gautam.DynamicPDFGenerator.controller;

import com.gautam.DynamicPDFGenerator.Model.Item;
import com.gautam.DynamicPDFGenerator.Model.PdfModel;
import com.gautam.DynamicPDFGenerator.service.PdfService;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;



@RestController
@RequestMapping("/api")
public class PdfController {

    private final PdfService pdfService;
    @Autowired
    PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    @PostMapping("/getPDF")
    public ResponseEntity<byte[]> getPdf(@RequestBody PdfModel pdf){

        try {
            byte[] pdfByteArray = pdfService.createPdf(pdf);

            // generate request header
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Disposition", "attachment; filename=generated.pdf");
            return new ResponseEntity<>(pdfByteArray, header, HttpStatus.OK);


        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
    }


