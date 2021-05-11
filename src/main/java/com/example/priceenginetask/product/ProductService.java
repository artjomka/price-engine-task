package com.example.priceenginetask.product;

import com.example.priceenginetask.pricing.PriceCalculator;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final PriceCalculator priceCalculator;
    private final ProductRepository productRepository;

    public ProductService(PriceCalculator priceCalculator, ProductRepository productRepository) {
        this.priceCalculator = priceCalculator;
        this.productRepository = productRepository;
    }

}
