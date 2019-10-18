package com.codegym.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Long,Product> cart = new HashMap<>();
    public Cart() {
    }
    public void addProduct(Product product) {
        cart.put(product.getId(),product);
    }
    public void removeProduct(long id){
        this.cart.remove(id);
    }
    public List<Product> getCart() {
        return new ArrayList<>(cart.values());
    }







}

