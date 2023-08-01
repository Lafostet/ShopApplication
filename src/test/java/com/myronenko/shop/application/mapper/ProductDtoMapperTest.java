package com.myronenko.shop.application.mapper;

import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.domain.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ProductDtoMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = ProductDtoMapper.class)
    public static class SpringTestConfig {
    }

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Test
    void convertToProductDto_WhenProductPassed_ThenProductDtoReturned() {
        Product product = buildProduct(1L, "Name", "99.99", "Description");

        ProductDto productDto = productDtoMapper.mapToProductDto(product);

        assertThat(productDto)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    void convertToProductDtos_WhenProductListPassed_ThenProductDtoListReturned(){
        Product product = buildProduct(2L, "Product", "100.10", "Goods");
        List<Product> productList = List.of(product);

        List<ProductDto> productDtoList = productDtoMapper.mapToProductDtos(productList);

        assertThat(productDtoList)
                .hasSameSizeAs(productList)
                .usingRecursiveComparison()
                .isEqualTo(productList);
    }

    private Product buildProduct(Long id, String name, String price, String description) {
        BigDecimal priceDecimal = new BigDecimal(price);
        return Product.builder()
                .setId(id)
                .setName(name)
                .setPrice(priceDecimal)
                .setDescription(description)
                .build();
    }

}
