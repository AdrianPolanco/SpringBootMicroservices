package com.adriandev.products.model.dtos;

import lombok.*;


//@Data agrega automaticamente los getters y setters basados en los campos de la clase
@Data
@AllArgsConstructor
@NoArgsConstructor
//Implementa el patron Builder
@Builder
public class ProductRequest {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
}
