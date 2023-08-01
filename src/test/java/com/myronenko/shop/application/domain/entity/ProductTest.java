package com.myronenko.shop.application.domain.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProductTest {

    @Test
    void builder_WhenCalledWithValues_ThenCreateInstanceWithFieldsProvided() {
        BigDecimal price = new BigDecimal("100.99");

        Product product = Product.builder()
                .setId(1L)
                .setName("Name")
                .setPrice(price)
                .setDescription("Desc")
                .build();

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Name");
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo("Desc");
    }

    @Test
    void constructor_WhenCalledWithReflection_ThenInstanceCreated() {
        assertThatCode(() -> Product.class.getDeclaredConstructor().newInstance()).doesNotThrowAnyException();
    }

    @Test
    void toString_WhenCalled_AllFieldsAreIncluded() {
        BigDecimal price = new BigDecimal("100.99");
        Product product = Product.builder()
                .setId(2L)
                .setName("Product")
                .setPrice(price)
                .setDescription("Descr")
                .build();

        assertThat(product.toString())
                .contains(
                        "id=2",
                        "name=Product",
                        "price=100.99",
                        "description=Descr"
                );
    }

    @Test
    void equalsAndHashcodeContract() {
        EqualsVerifier.simple()
                .forClass(Product.class)
                .withRedefinedSuperclass()
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }

}