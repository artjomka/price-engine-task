package com.example.priceenginetask.product;

import java.math.BigDecimal;

public record ProductConfiguration(Integer unitsPerBox, BigDecimal pricePerBox) {
}
