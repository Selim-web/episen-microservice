package com.episen.basket.service;


import com.episen.basket.model.Basket;
import com.episen.basket.repository.BasketRepository;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    public List<Basket> getAllBasket(){
        return basketRepository.gettAllBaskets();
    }

    public void addBasket(Basket basket){
        if(StringUtils.isEmpty(basket.getUsername()) || StringUtils.isEmpty(basket.getStatus())){
            throw new RuntimeException("Basket exception");
        }
        if(basketRepository.getBasketByUsername(basket.getUsername()) != null ){
            throw new RuntimeException("Item already exist");
        }
        // TODO Verifier que les items sont dans ItemInMemory
        basketRepository.addBasket(basket);
    }

    public Basket getBasketByUsername(String username){
        Basket basket = basketRepository.getBasketByUsername(username);
        if(basket == null){
            throw new RuntimeException("Basket not found");
        }
        return basket;
    }

    public void update(Basket basketToUpdate){
        if(null == basketRepository.getBasketByUsername(basketToUpdate.getUsername())){
            throw new RuntimeException("Item not found");
        }
        basketRepository.updateBasket(basketToUpdate);
    }

    public void delete(String username){
        if(null == basketRepository.getBasketByUsername(username)){
            throw new RuntimeException("Item not found");
        }
        basketRepository.deleteBasket(username);
    }

    public boolean isUserForThisUsername(String token, String username) throws ParseException {

        JWTClaimsSet jwtClaimsSet = getClaimsSet(token);
        String JwtUser = (String) jwtClaimsSet.getClaim("sub");
        System.out.println("token user is -> " + JwtUser);
        return JwtUser.equals(username);
    }

    private JWTClaimsSet getClaimsSet(String jwtToken) throws ParseException {
        JWSObject plainObject = JWSObject.parse(jwtToken);
        return JWTClaimsSet.parse(plainObject.getPayload().toJSONObject());
    }
}
