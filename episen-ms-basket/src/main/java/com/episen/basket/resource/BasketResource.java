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


    // METHODE JUST FOR ADMIN
    @GetMapping
    public ResponseEntity<List<Basket>> getAllBasket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && itemService.isAdmin(jwtToken)){
                return new ResponseEntity<List<Basket>>(basketService.getAllBasket(),HttpStatus.OK);
        }
        return new ResponseEntity<List<Basket>>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("{username}")
    public ResponseEntity<Basket> getBasketByUsername(@PathVariable("username")String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && basketService.isUserForThisUsername(jwtToken, username)){
            return new ResponseEntity<Basket>(basketService.getBasketByUsername(username), HttpStatus.OK);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<Basket> add(@RequestBody Basket basket, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && basketService.isUserForThisUsername(jwtToken, basket.getUsername())){
            basketService.addBasket(basket);
            return new ResponseEntity<Basket>(basket, HttpStatus.CREATED);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<Basket> update(@RequestBody Basket basket,  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && basketService.isUserForThisUsername(jwtToken, basket.getUsername())){
            basketService.update(basket);
            return new ResponseEntity<Basket>(basket, HttpStatus.OK);
        }
        return new ResponseEntity<Basket>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<String> delete(@PathVariable String username,  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        String[] bearerToken = token.split("Bearer ");
        String jwtToken = bearerToken[1];
        if(!basketService.isTokenExpired(jwtToken) && basketService.isUserForThisUsername(jwtToken, username)){
            basketService.delete(username);
            return new ResponseEntity<String>("Deleting Basket for this "+username, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Error with your Token", HttpStatus.UNAUTHORIZED);
    }






}
