package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByNameContaining(String firstName, Pageable pageable);

    Product findById(Long id);

    Product save(Product product);

    void remote(Long id);
}
