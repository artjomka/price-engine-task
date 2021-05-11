package com.example.priceenginetask.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
public class ProductDTO {
    String productId;
    List<UnitPriceDTO> unitPrices;

    public ProductDTO(String productId, List<UnitPriceDTO> unitPrices) {
        this.productId = productId;
        this.unitPrices = unitPrices;
    }

    public String getProductId() {
        return productId;
    }

    public List<UnitPriceDTO> getUnitPrices() {
        return unitPrices;
    }
}
