package com.gautam.DynamicPDFGenerator.Model;

import lombok.Data;

@Data
public class PdfModel {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;

    private Item[] items;
}
