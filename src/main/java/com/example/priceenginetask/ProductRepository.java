package com.example.priceenginetask;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductRepository {

    Optional<Product> getById(String id);

    List<Product> findAll();

    void save(Product product);
}
