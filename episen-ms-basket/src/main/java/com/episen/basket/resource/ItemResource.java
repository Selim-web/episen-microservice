package com.episen.basket.resource;


import com.episen.basket.model.Item;
import com.episen.basket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "item",produces = {"application/json"})
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<List<Item>>(itemService.getAllItems(), HttpStatus.OK);
    }

    // ONLY ADMIN USER
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Item item){
        //TODO verifier if JwtToken is Admin before add Item
        itemService.addItem(item);
        return new ResponseEntity<Object>(item, HttpStatus.OK);
    }

    // ONLY ADMIN USER
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Item itemToUpdate){
        //TODO verifier if JwtToken is Admin before add Item
        itemService.update(itemToUpdate);
        return new ResponseEntity<Object>(itemToUpdate, HttpStatus.OK);
    }

    // ONLY ADMIN USER
    @DeleteMapping("{gtnToDelete}")
    public ResponseEntity<Object> delete(@PathVariable Integer gtnToDelete){
        //TODO verifier if JwtToken is Admin before add Item
        itemService.delete(gtnToDelete);
        return new ResponseEntity<Object>(gtnToDelete, HttpStatus.OK);
    }
}
