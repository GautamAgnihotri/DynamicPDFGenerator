package com.gautam.DynamicPDFGenerator.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PdfModel {
    @NotEmpty(message = "Seller name cannot be empty")
    @Size(min = 2, message = "Seller name must be at least 2 characters")
    private String seller;

    @NotEmpty(message ="Seller GSTIN cannot be empty")
    @Pattern(regexp = "[0-9A-Z]{15}", message = "Invalid Seller GSTIN format")
    private String sellerGstin;

    @NotEmpty(message = "Seller address cannot be empty")
    private String sellerAddress;

    @NotEmpty(message = "Buyer name cannot be empty")
    private String buyer;

    @NotEmpty(message = "Buyer GSTIN cannot be empty")
    @Pattern(regexp = "[0-9A-Z]{15}", message = "Invalid Buyer GSTIN format")
    private String buyerGstin;

    @NotEmpty(message = "Buyer address cannot be empty")
    private String buyerAddress;

    @NotEmpty(message = "Items cannot be empty")
    private Item[] items;
}
