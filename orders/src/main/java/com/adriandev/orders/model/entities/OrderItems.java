package com.adriandev.orders.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table //Si no se le proporciona un nombre como argumento, la tabla generada tendra el mismo nombre de la clase
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Double price;
    public Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
