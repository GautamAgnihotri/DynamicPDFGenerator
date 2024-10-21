package com.gautam.DynamicPDFGenerator.utility;

import com.gautam.DynamicPDFGenerator.DTO.PdfModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class GenerateHash {

    public static String getHash(PdfModel pdfModel){
        String data = pdfModel.toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }catch (Exception e){
           System.out.println("error in hash generation : "+e.getMessage());
            return null;
        }

    }
}
