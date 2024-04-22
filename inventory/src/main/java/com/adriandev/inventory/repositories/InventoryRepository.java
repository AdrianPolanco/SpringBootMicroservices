package com.adriandev.inventory.repositories;

import com.adriandev.inventory.model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //Unlike .NET we don't need to implement the findBySku method ourselves since JPA generates automatically
    //an implementation at runtime based on the entity name (Inventory of InventoryRepository in this case)
    //and the name of the method (sku and findBySku)
    Optional<Inventory> findBySku(String sku);

    List<Inventory> findBySkuIn(List<String> skus);
}
