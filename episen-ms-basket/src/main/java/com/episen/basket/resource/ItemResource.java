package com.episen.basket.resource;


import com.episen.basket.model.Item;
import com.episen.basket.service.BasketService;
import com.episen.basket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "item",produces = {"application/json"})
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BasketService basketService;

    // ALL USER
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken)){
            return new ResponseEntity<List<Item>>(itemService.getAllItems(), HttpStatus.OK);
        }
        return new ResponseEntity<List<Item>>(HttpStatus.UNAUTHORIZED);
    }

    // ONLY ADMIN USER
    @PostMapping
    public ResponseEntity<Item> add(@RequestBody Item item, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && itemService.isAdmin(jwtToken)){
            itemService.addItem(item);
            return new ResponseEntity<Item>(item, HttpStatus.CREATED);
        }
        return new ResponseEntity<Item>(HttpStatus.UNAUTHORIZED);
    }

    // ONLY ADMIN USER
    @PutMapping
    public ResponseEntity<Item> update(@RequestBody Item itemToUpdate, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && itemService.isAdmin(jwtToken)){
            itemService.update(itemToUpdate);
            return new ResponseEntity<Item>(itemToUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<Item>(HttpStatus.UNAUTHORIZED);
    }

    // ONLY ADMIN USER
    @DeleteMapping("{gtnToDelete}")
    public ResponseEntity<Long> delete(@PathVariable Long gtnToDelete, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && itemService.isAdmin(jwtToken)){
            itemService.delete(gtnToDelete);
            return new ResponseEntity<Long>(gtnToDelete, HttpStatus.OK);
        }
        return new ResponseEntity<Long>(HttpStatus.UNAUTHORIZED);
    }
}
