package com.example.priceenginetask.product;

import com.example.priceenginetask.pricing.PriceCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public static final int DEFAULT_AMOUNT_OF_ITEMS = 50;
    private final PriceCalculator priceCalculator;
    private final ProductRepository productRepository;

    public ProductService(PriceCalculator priceCalculator, ProductRepository productRepository) {
        this.priceCalculator = priceCalculator;
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProductsPrices(int amountOfItemsCalculateFor) {
        var result = new ArrayList<ProductDTO>();
        var products = productRepository.findAll();
        for (Product product : products) {
            var unitPricesDto = new ArrayList<UnitPriceDTO>(amountOfItemsCalculateFor);
            var productDTO = new ProductDTO(product.id(), unitPricesDto);
            for (int unitAmount = 1; unitAmount < amountOfItemsCalculateFor + 1; unitAmount++) {
                BigDecimal price = priceCalculator.calculatePrice(product, unitAmount);
                unitPricesDto.add(new UnitPriceDTO(unitAmount, price));
            }
            productDTO.unitPrices = unitPricesDto;
            result.add(productDTO);
        }
        return result;
    }

    public ProductDTO getProductPrice(String id, Integer units) {
        var product = productRepository.getById(id).orElseThrow(() -> new RuntimeException("Product not found by id " + id));
        BigDecimal price = priceCalculator.calculatePrice(product, units);
        List<UnitPriceDTO> unitPricesDto = new ArrayList<>();
        unitPricesDto.add(new UnitPriceDTO(units, price));
        return new ProductDTO(id, unitPricesDto);
    }
}
