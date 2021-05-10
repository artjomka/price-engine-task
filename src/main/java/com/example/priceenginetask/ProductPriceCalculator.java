package com.example.priceenginetask;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.*;

@Service
public class ProductPriceCalculator implements PriceCalculator {

    private final ProductRepository productRepository;

    public ProductPriceCalculator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal calculatePrice(String productId, Integer units) {
        var product = productRepository.getById(productId)
                .orElseThrow(() -> new RuntimeException("No product found by id " + productId));
        var totalAmountOfBoxes = units / product.productUnit().unitsPerBox();
        var manuallyPickedItems = units % product.productUnit().unitsPerBox();
        var priceForBoxes = calculateProductBoxesPrice(totalAmountOfBoxes, product.productUnit().pricePerBox());
        var priceForUnits = calculatePriceForUnits(manuallyPickedItems, product.productUnit());
        return priceForBoxes.add(priceForUnits);
    }

    private BigDecimal calculatePriceForUnits(Integer manuallyPickedItems, ProductUnit productUnit) {
        if (manuallyPickedItems == 0) {
            return ZERO;
        }

        var totalAmountOfManuallyPickedItems = productUnit.pricePerBox()
                .divide(valueOf(productUnit.unitsPerBox()), RoundingMode.HALF_UP)
                .multiply(valueOf(manuallyPickedItems));

        return totalAmountOfManuallyPickedItems.multiply(BigDecimal.valueOf(0.3)).add(totalAmountOfManuallyPickedItems);
    }

    private boolean isApplicableForDiscount(int totalAmountOfBoxes) {
        return totalAmountOfBoxes > 2;
    }

    private BigDecimal calculateProductBoxesPrice(Integer totalAmountOfBoxes, BigDecimal pricePerBox) {
        if (isApplicableForDiscount(totalAmountOfBoxes)) {
            BigDecimal totalPrice = pricePerBox.multiply(valueOf(totalAmountOfBoxes));
            BigDecimal discount = totalPrice.multiply(valueOf(0.1));
            return totalPrice.subtract(discount);
        }
        return pricePerBox.multiply(valueOf(totalAmountOfBoxes));
    }

}
