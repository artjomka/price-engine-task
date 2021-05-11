package com.example.priceenginetask.product

import com.example.priceenginetask.pricing.PriceCalculator
import spock.lang.Specification
import spock.lang.Subject

class ProductServiceSpec extends Specification {

    def productRepository = Mock(ProductRepository)
    def priceCalculator = Mock(PriceCalculator)

    @Subject
    def productService = new ProductService(priceCalculator, productRepository)

    def "should create proper result dtos for 2 product"() {
        given:
            priceCalculator.calculatePrice(_ as Product, _ as Integer) >> 10.0
            productRepository.findAll() >> [new Product("product1", new ProductConfiguration(10, 10.0)),
                                            new Product("product2", new ProductConfiguration(12, 12.0))]
        when:
            def productsPrices = productService.getProductsPrices(10)
        then:
            productsPrices.size() == 2
            def firstProduct = productsPrices.find {
                it.productId == "product1"
            }
            firstProduct.unitPrices.size() == 10
            def second = productsPrices.find {
                it.productId == "product2"
            }
            second.unitPrices.size() == 10
    }
}
