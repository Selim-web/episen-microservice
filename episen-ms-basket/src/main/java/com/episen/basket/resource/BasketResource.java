package com.episen.basket.resource;


import com.episen.basket.model.Basket;
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
@RequestMapping(value = "basket", produces = {"application/json"})
public class BasketResource {

    @Autowired
    private BasketService basketService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Basket>> getAllBasket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        // TODO Bloquer la methode juste pour l'admin
        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(itemService.isAdmin(jwtToken)){
            return new ResponseEntity<List<Basket>>(basketService.getAllBasket(),HttpStatus.OK);
        }
        return new ResponseEntity<List<Basket>>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("{username}")
    public ResponseEntity<Basket> getBasketByUsername(@PathVariable("username")String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(basketService.isUserForThisUsername(jwtToken, username)){
            return new ResponseEntity<Basket>(basketService.getBasketByUsername(username), HttpStatus.OK);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<Basket> add(@RequestBody Basket basket, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        // TODO Verifier la validiter du token avec le BasketService
        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(basketService.isUserForThisUsername(jwtToken, basket.getUsername())){
            basketService.addBasket(basket);
            return new ResponseEntity<Basket>(basket, HttpStatus.OK);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<Basket> update(@RequestBody Basket basket,  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        // TODO Verifier la validiter du token avec le BasketService
        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(basketService.isUserForThisUsername(jwtToken, basket.getUsername())){
            basketService.update(basket);
            return new ResponseEntity<Basket>(basket, HttpStatus.OK);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<String> delete(@PathVariable String username,  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        // TODO Verifier la validiter du token avec le BasketService
        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(basketService.isUserForThisUsername(jwtToken, username)){
            basketService.delete(username);
            return new ResponseEntity<String>("Deleting Basket for this "+username, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Error in your Token", HttpStatus.UNAUTHORIZED);

    }






}
