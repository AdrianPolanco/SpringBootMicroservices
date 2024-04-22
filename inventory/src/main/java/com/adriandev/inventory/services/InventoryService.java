package com.adriandev.inventory.services;

import com.adriandev.inventory.model.dtos.BaseResponse;
import com.adriandev.inventory.model.dtos.OrderItemRequest;
import com.adriandev.inventory.model.entities.Inventory;
import com.adriandev.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        //Optional has .filter method, which receives a Predicate<T>, a lamda function that returns a boolean
        //if a value meets a criteria
        //.isPresent() is a method of Optional class, that returns true if the value got from filter is not empty
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream()
                    .filter(inventoryItem -> inventoryItem.getSku().equals(orderItem.getSku()))
                    .findFirst();
    //inventory.get().getQuantity() invokes the .get method from Optional<Inventory> and will return an instance of
            //Inventory is there is such found instance
            if(inventory.isEmpty()) errorList.add("Product with sku " + orderItem.getSku() + " does not exist");
            else if(inventory.get().getQuantity() < orderItem.getQuantity())
                errorList.add("Product with sku " + orderItem.getSku() + " has insufficient quantity");
        });

        //new String[0] will create an array of the same size as the errorList without using additional memory
        //thus, is more efficient than new String[errorList.size()]
        return errorList.size() > 0 ?
                new BaseResponse(errorList.toArray(new String[0]))
                : new BaseResponse(null);
    }
}
