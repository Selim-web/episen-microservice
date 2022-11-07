package com.episen.basket.repository;


import com.episen.basket.model.Basket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BasketRepository {

    private Map<String, Basket> basketInMemory = new HashMap<>();

    public void addBasket (Basket basket) {
        System.out.println("addbasket -> gtn : " + basket.getUsername());
        basketInMemory.put(basket.getUsername(), basket);
    }

    public List<Basket> gettAllBaskets() {
        System.out.println("Get All -> count : " + basketInMemory.size());
        return new ArrayList<>(basketInMemory.values());
    }

    public Basket getBasketByUsername(String username) {
        System.out.println("Get basket by gtn -> basket : " + username);
        return basketInMemory.get(username);
    }

    public void updateBasket(Basket basketToUpdate){
        System.out.println("Update basket by gtn -> basket : " + basketToUpdate.getUsername());
        basketInMemory.put(basketToUpdate.getUsername(),basketToUpdate);
    }

    public void deleteBasket(String username){
        System.out.println("Remove basket by gtn -> basket : " + username);
        basketInMemory.remove(username);
    }

}
