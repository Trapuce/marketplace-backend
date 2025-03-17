package com.trapuce.marketplace.models;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "attributes")
@Entity
@NoArgsConstructor
public class Attribute {
     @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(name = "attribute_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeType attributeType;
    
    @Column(name = "is_required", nullable = false)
    private boolean required;
    

    @JdbcTypeCode(SqlTypes.JSON)  
    @Column(name = "possible_values", columnDefinition = "jsonb")
    private Map<String, String> possibleValues;
    public Attribute(String name, Category category, AttributeType attributeType, boolean required, Map<String, String> possibleValues) {
        this.name = name;
        this.category = category;
        this.attributeType = attributeType;
        this.required = required;
        this.possibleValues = possibleValues;
    }
   
}
