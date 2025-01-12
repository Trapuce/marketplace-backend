package com.trapuce.marketplace.dtos;

import lombok.Data;

@Data
public class PhotoDto {
    private String url;   
    private byte[] content; 
    private int order;   
}
