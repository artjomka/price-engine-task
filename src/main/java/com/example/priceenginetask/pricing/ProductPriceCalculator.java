package com.example.priceenginetask.pricing;

import com.example.priceenginetask.product.Product;
import com.example.priceenginetask.product.ProductConfiguration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

@Service
public class ProductPriceCalculator implements PriceCalculator {

    public static final double HAND_PICK_ITEM_PRICE_INCREASE = 0.3;
    public static final int APPLICABLE_FOR_DISCOUNT_AMOUNT = 2;
    public static final double DISCOUNT = 0.1;

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

        return totalAmountOfManuallyPickedItems.multiply(BigDecimal.valueOf(HAND_PICK_ITEM_PRICE_INCREASE)).add(totalAmountOfManuallyPickedItems);
    }

    private boolean isApplicableForDiscount(int totalAmountOfBoxes) {
        return totalAmountOfBoxes > APPLICABLE_FOR_DISCOUNT_AMOUNT;
    }

    private BigDecimal calculateProductBoxesPrice(Integer totalAmountOfBoxes, BigDecimal pricePerBox) {
        if (isApplicableForDiscount(totalAmountOfBoxes)) {
            BigDecimal totalPrice = pricePerBox.multiply(valueOf(totalAmountOfBoxes));
            BigDecimal discount = totalPrice.multiply(valueOf(DISCOUNT));
            return totalPrice.subtract(discount);
        }
        return pricePerBox.multiply(valueOf(totalAmountOfBoxes));
    }

}
