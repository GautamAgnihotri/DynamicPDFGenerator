package com.gautam.DynamicPDFGenerator.service;

import com.gautam.DynamicPDFGenerator.dto.Item;
import com.gautam.DynamicPDFGenerator.dto.PdfModel;
import com.gautam.DynamicPDFGenerator.utility.GenerateHash;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;


@Slf4j
@Service
public class PdfService {

    //directory for storing generated PDF
    @Value("${generated_PDF_Dir_Path}")
    private String PDF_STORAGE_DIR;

    public byte[] createPdf(PdfModel pdfModel) throws Exception {

        log.info("Starting PDF generation for seller: {}, buyer: {}", pdfModel.getSeller(), pdfModel.getBuyer());


        //Check whether dir path exists or not
        File storageDir = new File(PDF_STORAGE_DIR);
        if(!storageDir.exists()){
            storageDir.mkdirs();   // Create the directory if it doesn't exist
        }

        //Create hash of the input data
        String pdfHash = GenerateHash.getHash(pdfModel);
        log.debug("Generated hash for PDF: {}", pdfHash);

        //check if PDF already existes in local storage
        File existingPdf = new File(PDF_STORAGE_DIR+pdfHash+".pdf");
        if(existingPdf.exists()){
            log.info("PDF found in local storage, returning existing file: {}", existingPdf.getPath());
            return Files.readAllBytes(existingPdf.toPath());
        }

        // Log PDF generation start
        log.info("Generating new PDF for hash: {}", pdfHash);

        //Generate the PDF if it doesn't exist
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Initialize PDF Writer
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add Header Table (Seller and Buyer)
        Table headerTable = new Table(2);
        headerTable.setWidth(520F);

        Cell sellerCell = new Cell();
        Paragraph sellerPara = new Paragraph("Seller:\n" + pdfModel.getSeller() + "\n" + pdfModel.getSellerAddress() + "\nGSTIN: " + pdfModel.getSellerGstin());
        sellerCell.add(sellerPara).setPadding(20F);
        headerTable.addCell(sellerCell);

        Cell buyerCell = new Cell();
        Paragraph buyerPara = new Paragraph("Buyer:\n" + pdfModel.getBuyer() + "\n" + pdfModel.getBuyerAddress() + "\nGSTIN: " + pdfModel.getBuyerGstin());
        buyerCell.add(buyerPara).setPadding(20F);
        headerTable.addCell(buyerCell);

        document.add(headerTable);

        // Add Items Table
        Table itemTable = new Table(4);
        itemTable.setWidth(520F);

        // Add table headers
        addTableHeader(itemTable);

        // Add table rows
        addItemsToTable(itemTable, pdfModel.getItems());

        document.add(itemTable);

        Table footerTable = new Table(1);
        footerTable.setWidth(520F);
        footerTable.setHeight(50F);
        footerTable.addCell(new Cell());
        document.add(footerTable);

        // Close document and return bytes
        document.close();
        pdfDoc.close();

        try(FileOutputStream fos = new FileOutputStream(existingPdf)){
            fos.write(baos.toByteArray()); // Write the PDF to the file.
            log.info("PDF successfully generated and saved to: {}", existingPdf.getPath());
        } catch (IOException e){
            throw new IOException("Exception occurred during PDF Creation");
        }

        return baos.toByteArray();
    }

    private void addTableHeader(Table table) {
        table.addCell(new Cell().add(new Paragraph("Item")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph("Quantity")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph("Rate")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph("Amount")).setTextAlignment(TextAlignment.CENTER));
    }

    private void addItemsToTable(Table table, Item[] items) {
        for (Item item : items) {
            table.addCell(new Cell().add(new Paragraph(item.getName())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(item.getQuantity())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))).setTextAlignment(TextAlignment.CENTER));
        }
    }
}
