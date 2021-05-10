package com.example.priceenginetask;

import java.math.BigDecimal;

public record Product(String id, ProductUnit productUnit) {
}

record ProductUnit(Integer unitsPerBox, BigDecimal pricePerBox) {

}
