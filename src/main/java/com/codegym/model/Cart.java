package com.codegym.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Long, Product> productMap = new HashMap<>();

    public List<Product> listProduct() {
        return new ArrayList<>(productMap.values());
    }
}
