package com.adriandev.inventory.controllers;

import com.adriandev.inventory.model.dtos.BaseResponse;
import com.adriandev.inventory.model.dtos.OrderItemRequest;
import com.adriandev.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.BaseRowSet;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku") String sku){
        return inventoryService.isInStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems){
        return inventoryService.areInStock(orderItems);
    }
}
