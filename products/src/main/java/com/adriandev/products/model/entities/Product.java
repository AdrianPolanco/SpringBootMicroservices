package com.adriandev.products.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String name;
    private String description;
    private  Double price;
    private Boolean status;

    @Override
    public String toString(){
        return "Product {" + id + ", name = " + name + ", description = " + description + " }";
    }
}
