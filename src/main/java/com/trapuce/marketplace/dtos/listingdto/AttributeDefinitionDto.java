package com.trapuce.marketplace.dtos.listingdto;

import java.util.Map;

public class AttributeDefinitionDto {
    private String name;
    private String attributeType;
    private boolean required;
    private Map<String, String> possibleValues;
    
}
