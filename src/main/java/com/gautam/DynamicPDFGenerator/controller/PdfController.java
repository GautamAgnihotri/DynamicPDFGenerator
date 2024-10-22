package com.gautam.DynamicPDFGenerator.controller;

import com.gautam.DynamicPDFGenerator.dto.PdfModel;
import com.gautam.DynamicPDFGenerator.service.PdfService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/api")
public class PdfController {

    private final PdfService pdfService;
    private HttpServletRequest request;
    @Autowired
    PdfController(PdfService pdfService, HttpServletRequest request) {
        this.pdfService = pdfService;
        this.request = request;
    }
    @PostMapping("/getPDF")
    public ResponseEntity<byte[]> getPdf(@Valid @RequestBody PdfModel pdf){
        log.info("Request hit from : "+request.getRemoteAddr());
        try {
            byte[] pdfByteArray = pdfService.createPdf(pdf);

            // generate request header
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Disposition", "attachment; filename=generated.pdf");
            return new ResponseEntity<>(pdfByteArray, header, HttpStatus.OK);


        }
        catch (IOException e){
            log.error("Error while generating PDF :"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e) {
            log.error("Exception occured :"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
    }


