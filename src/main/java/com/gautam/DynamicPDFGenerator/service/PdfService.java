package com.gautam.DynamicPDFGenerator.service;

import com.gautam.DynamicPDFGenerator.Model.Item;
import com.gautam.DynamicPDFGenerator.Model.PdfModel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
    public byte[] createPdf(PdfModel pdfModel) throws Exception {
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
