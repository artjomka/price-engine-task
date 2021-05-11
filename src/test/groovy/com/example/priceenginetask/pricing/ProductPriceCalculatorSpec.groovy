package com.example.priceenginetask.pricing

import com.example.priceenginetask.pricing.ProductPriceCalculator
import com.example.priceenginetask.product.Product
import com.example.priceenginetask.product.ProductConfiguration
import spock.lang.Specification
import spock.lang.Subject

class ProductPriceCalculatorSpec extends Specification {


    @Subject
    def productPriceCalculator = new ProductPriceCalculator()

    def "Should calculate price for product one box"() {
        given:
            def product = new Product("product1", new ProductConfiguration(20, 175.00))
        when:
            def price = productPriceCalculator.calculatePrice(product, 20)
        then:
            price == 175.00
    }

    def "Should calculate price for less the box units applying 30% manual pick"() {
        given:
            def product = new Product("product1", new ProductConfiguration(20, 200.00))
        when:
            def price = productPriceCalculator.calculatePrice(product, 5)
        then:
            price == 65.00
    }

    def "Should calculate price with discount for more than 2 box "() {
        given:
            def product = new Product("product1", new ProductConfiguration(20, 200.00))
        when:
            def price = productPriceCalculator.calculatePrice(product, 60)
        then:
            price == 540.00
    }

    def "Should calculate price with discount for box amount but additional cost for manual pick"() {
        given:
            def product = new Product("product1", new ProductConfiguration(20, 200.00))
        when:
            def price = productPriceCalculator.calculatePrice(product, 65)
        then:
            price == 605.00
    }
}
