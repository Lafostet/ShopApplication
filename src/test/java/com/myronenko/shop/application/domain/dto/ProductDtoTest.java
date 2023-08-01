package com.myronenko.shop.application.domain.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProductDtoTest {

    @Test
    void builder_WhenCalledWithValues_ThenCreateInstanceWithFieldsProvided() {
        BigDecimal price = new BigDecimal("1000.6");
        ProductDto productDto = ProductDto.builder()
                .setId(1L)
                .setName("Name")
                .setPrice(price)
                .setDescription("Description")
                .build();

        assertThat(productDto.getId()).isEqualTo(1L);
        assertThat(productDto.getName()).isEqualTo("Name");
        assertThat(productDto.getPrice()).isEqualTo(price);
        assertThat(productDto.getDescription()).isEqualTo("Description");
    }

    @Test
    void constructor_WhenCalledWithReflection_ThenInstanceCreated() {
        assertThatCode(() -> ProductDto.class.getDeclaredConstructor().newInstance())
                .doesNotThrowAnyException();
    }

}
