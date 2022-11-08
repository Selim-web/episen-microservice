package com.episen.basket.service;


import com.episen.basket.model.Item;
import com.episen.basket.repository.ItemRepository;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item getItemByGtn(Long gtn){
        Item item = itemRepository.getItemByGtn(gtn);
        if(item == null){
            throw new RuntimeException("Item not found");
        }
        return item;
    }

    public List<Item> getAllItems(){
        return itemRepository.gettAllItems();
    }

    public boolean isItemInMemory(List<Item> itemList) {
        boolean isKeyPresent = true;
        for (Item item : itemList) {
            if (!itemRepository.isItemInMemory(item)) {
                isKeyPresent = false;
            }
        }
        return isKeyPresent;
    }

    public void addItem(Item item){
        if(StringUtils.isEmpty(item.getGtn().toString()) || StringUtils.isEmpty(item.getLabel())){
            throw new RuntimeException("Item exception");
        }
        if(itemRepository.getItemByGtn(item.getGtn()) != null ){
            throw new RuntimeException("Item already exist");
        }
        itemRepository.addItem(item);
    }

    public void update(Item itemToUpdate){
        if(null == itemRepository.getItemByGtn(itemToUpdate.getGtn())){
            throw new RuntimeException("Item not found");
        }
        itemRepository.updateItem(itemToUpdate);
    }

    public void delete(Long gtntoDelete){
        if(null == itemRepository.getItemByGtn(gtntoDelete)){
            throw new RuntimeException("Item not found");
        }
        itemRepository.deleteItem(gtntoDelete);
    }

    public boolean isAdmin(String token) throws ParseException {
        JWTClaimsSet jwtClaimsSet = getClaimsSet(token);
        List<String> authUserRoles = (List<String>) jwtClaimsSet.getClaim("ROLES");
        return authUserRoles.contains("ADMIN");
    }

    private JWTClaimsSet getClaimsSet(String jwtToken) throws ParseException {
        JWSObject plainObject = JWSObject.parse(jwtToken);
        return JWTClaimsSet.parse(plainObject.getPayload().toJSONObject());
    }

}
