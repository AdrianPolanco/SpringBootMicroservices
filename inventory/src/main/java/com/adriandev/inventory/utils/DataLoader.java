package com.adriandev.inventory.utils;

import com.adriandev.inventory.model.entities.Inventory;
import com.adriandev.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component //Indicates to Spring that it is a component and may be injected if required to other parts of application
@RequiredArgsConstructor
@Slf4j //Provides a logger
public class DataLoader implements CommandLineRunner {
    /*CommandLineRunners is an interface, which makes that the run method in a class that implements it
    gets executed as soon as the program is bootstraped*/
    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        if(inventoryRepository.findAll().size() == 0) {
            log.info("Loading data...");
            //List.of() returns an immutable List with the items specified inside its parameters
            inventoryRepository.saveAll(List.of(
                    Inventory.builder().sku("0001").quantity(30L).build(),
                    Inventory.builder().sku("0002").quantity(54L).build(),
                    Inventory.builder().sku("0003").quantity(12L).build(),
                    Inventory.builder().sku("0004").quantity(90L).build()
            ));

            log.info("Data loaded.");
        }
    }
}
