package com.example.priceenginetask.pricing;

import com.example.priceenginetask.product.Product;

import java.math.BigDecimal;

public interface PriceCalculator {
    BigDecimal calculatePrice(Product productId, Integer units);

}
