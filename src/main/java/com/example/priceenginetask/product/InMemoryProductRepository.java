package com.example.priceenginetask.product;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private Map<String, Product> productMap = new HashMap<>();

    public InMemoryProductRepository() {
        var product1 = new Product("product1", new ProductConfiguration(20, valueOf(175)));
        var product2 = new Product("product2", new ProductConfiguration(5, valueOf(175)));
        productMap.put("product1", product1);
        productMap.put("product2", product2);
    }

    @Override
    public Optional<Product> getById(String id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> findAll() {
        return productMap.values().stream().toList();
    }

    @Override
    public void save(Product product) {
        productMap.putIfAbsent(product.id(), product);
    }
}
