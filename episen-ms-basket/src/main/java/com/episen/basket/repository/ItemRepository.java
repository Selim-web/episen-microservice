package com.episen.basket.repository;

import com.episen.basket.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemRepository {

    private Map<String, Item> itemInMemory = new HashMap<>();

    public void addItem (Item item) {
        System.out.println("addItem -> gtn : " + item.getGtn());
        itemInMemory.put(item.getGtn().toString(), item);
    }

    public List<Item> gettAllItems() {
        System.out.println("Get All -> count : " + itemInMemory.size());
        return new ArrayList<>(itemInMemory.values());
    }

    public Item getItemByGtn(Integer gtn) {
        System.out.println("Get Item by gtn -> item : " + gtn);
        return itemInMemory.get(gtn.toString());
    }

    public void updateItem(Item itemToUpdate){
        System.out.println("Update Item by gtn -> item : " + itemToUpdate.getGtn());
        itemInMemory.put(itemToUpdate.getGtn().toString(),itemToUpdate);
    }

    public void deleteItem(Integer gtnToRemove){
        System.out.println("Remove Item by gtn -> item : " + gtnToRemove);
        itemInMemory.remove(gtnToRemove.toString());
    }



}
