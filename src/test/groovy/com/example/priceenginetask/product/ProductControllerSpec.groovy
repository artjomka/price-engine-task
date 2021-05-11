package com.example.priceenginetask.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def "should retrieve products with prices via endpoint"() {
        when:
            def responseEntity = restTemplate.getForEntity("/products/price", ProductDTO[].class, [])
        then:
            responseEntity.statusCodeValue == 200
            responseEntity.body.size() == 2
    }

    def "should retrieve product price for units"() {
        when:
            def responseEntity = restTemplate.getForEntity("/products/product1?units=5", ProductDTO.class)
        then:
            responseEntity.statusCodeValue == 200
            responseEntity.body != null
            responseEntity.body.productId == "product1"
            responseEntity.body.unitPrices.size() == 1
            responseEntity.body.unitPrices.first().price() == 58.5
    }
}
