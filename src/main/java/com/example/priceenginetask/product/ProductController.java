package com.example.priceenginetask.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/price")
    public ResponseEntity<List<ProductDTO>> getProductsPrice() {
        return ResponseEntity.ok(productService.getProductsPrices(ProductService.DEFAULT_AMOUNT_OF_ITEMS));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductPricePerUnits(@PathVariable String id, @RequestParam(value = "units") Integer items) {
        return ResponseEntity.ok(productService.getProductPrice(id, items));
    }
}
