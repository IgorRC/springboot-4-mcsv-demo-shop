package com.igor.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.igor.springcloud.msvc.items.models.Item;
import com.igor.springcloud.msvc.items.services.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ItemComtroller{
    private final ItemService itemService;

    public ItemComtroller(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> list () {
        return itemService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> details (@PathVariable Long id) {
        Optional<Item> itemOptional = itemService.findById(id);
        if(itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                    "message", 
                    "Product not found en microservice msvc-products"));
    }
}
