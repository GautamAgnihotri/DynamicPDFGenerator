package com.gautam.DynamicPDFGenerator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Item {
    @NotEmpty(message = "Item name cannot be empty")
    private String name;

    @NotEmpty(message = "Item quantity cannot be empty")
    private String quantity;

    @Positive(message = "Rate must be positive")
    private double rate;

    @Min(value = 1, message = "Amount must be at least 1")
    private double amount;
}