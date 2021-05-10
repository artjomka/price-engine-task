package com.example.priceenginetask;

import java.math.BigDecimal;

public interface PriceCalculator {
    BigDecimal calculatePrice(String productId, Integer units);

}
