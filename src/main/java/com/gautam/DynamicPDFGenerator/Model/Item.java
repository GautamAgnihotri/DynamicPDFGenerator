package com.gautam.DynamicPDFGenerator.Model;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String quantity;

    private float rate;
    private float amount;
}