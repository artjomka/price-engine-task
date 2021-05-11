package com.example.priceenginetask.pricing;

import com.example.priceenginetask.product.Product;
import com.example.priceenginetask.product.ProductConfiguration;
import com.example.priceenginetask.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.*;

@Service
public class ProductPriceCalculator implements PriceCalculator {

    @Override
    public BigDecimal calculatePrice(Product product, Integer units) {
        var totalAmountOfBoxes = units / product.productConfiguration().unitsPerBox();
        var manuallyPickedItems = units % product.productConfiguration().unitsPerBox();
        var priceForBoxes = calculateProductBoxesPrice(totalAmountOfBoxes, product.productConfiguration().pricePerBox());
        var priceForUnits = calculatePriceForUnits(manuallyPickedItems, product.productConfiguration());
        return priceForBoxes.add(priceForUnits);
    }

    private BigDecimal calculatePriceForUnits(Integer manuallyPickedItems, ProductConfiguration productConfiguration) {
        if (manuallyPickedItems == 0) {
            return ZERO;
        }

        var totalAmountOfManuallyPickedItems = productConfiguration.pricePerBox()
                .divide(valueOf(productConfiguration.unitsPerBox()), RoundingMode.HALF_UP)
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
