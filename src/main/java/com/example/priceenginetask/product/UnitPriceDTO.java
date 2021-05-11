package com.example.priceenginetask.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
public record UnitPriceDTO(Integer units, BigDecimal price) {
}
