package com.scaler.ekart.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Product extends BaseModel implements Serializable{
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.ALL})
    private Category category;
}
