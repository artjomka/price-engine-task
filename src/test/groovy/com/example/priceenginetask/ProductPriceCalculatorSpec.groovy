package com.example.priceenginetask

import spock.lang.Specification
import spock.lang.Subject


class ProductPriceCalculatorSpec extends Specification {

    def productRepository = Mock(ProductRepository)

    @Subject
    def productPriceCalculator = new ProductPriceCalculator(productRepository)

    def "Should calculate price for product one box"() {
        given:
            def product = new Product("product1", new ProductUnit(20, 175.00))
            productRepository.getById("product1") >> Optional.of(product)
        when:
            def price = productPriceCalculator.calculatePrice("product1", 20)
        then:
            price == 175.00
    }

    def "Should calculate price for less the box units applying 30% manual pick"() {
        given:
            def product = new Product("product1", new ProductUnit(20, 200.00))
            productRepository.getById("product1") >> Optional.of(product)
        when:
            def price = productPriceCalculator.calculatePrice("product1", 5)
        then:
            price == 65.00
    }

    def "Should calculate price with discount for more than 2 box "() {
        given:
            def product = new Product("product1", new ProductUnit(20, 200.00))
            productRepository.getById("product1") >> Optional.of(product)
        when:
            def price = productPriceCalculator.calculatePrice("product1", 60)
        then:
            price == 540.00
    }

    def "Should calculate price with discount for box amount but additional cost for manual pick"() {
        given:
            def product = new Product("product1", new ProductUnit(20, 200.00))
            productRepository.getById("product1") >> Optional.of(product)
        when:
            def price = productPriceCalculator.calculatePrice("product1", 65)
        then:
            price == 605.00
    }
}
