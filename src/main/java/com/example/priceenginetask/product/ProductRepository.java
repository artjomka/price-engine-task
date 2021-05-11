package com.example.priceenginetask.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getById(String id);

    List<Product> findAll();

    void save(Product product);
}
