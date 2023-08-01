package com.myronenko.shop.application.mapper;

import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.domain.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = ProductMapper.class)
    public static class SpringTestConfig {
    }

    @Autowired
    private ProductMapper productMapper;

    @Test
    void convertToProduct_WhenProductDtoPassed_ThenProductReturned() {
        ProductDto productDto = buildProduct(1L, "Name", "99.99", "Description");

        Product product = productMapper.mapToProduct(productDto);

        assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productDto);
    }

    private ProductDto buildProduct(Long id, String name, String price, String description) {
        BigDecimal priceDecimal = new BigDecimal(price);
        return ProductDto.builder()
                .setId(id)
                .setName(name)
                .setPrice(priceDecimal)
                .setDescription(description)
                .build();
    }

}
