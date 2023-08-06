package com.myronenko.shop.application.service.product.impl;

import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.domain.entity.Product;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import com.myronenko.shop.application.mapper.ProductDtoMapper;
import com.myronenko.shop.application.mapper.ProductMapper;
import com.myronenko.shop.application.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DefaultProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductDtoMapper productDtoMapper;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private DefaultProductService testee;

    @Test
    void getAllProducts_WhenProductsExist_ThenReturnProductsList() {
        Product product = Product.builder().build();
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        List<ProductDto> productDtos = mockProducts();
        when(productDtoMapper.mapToProductDtos(products)).thenReturn(productDtos);

        List<ProductDto> result = testee.getAllProducts();

        assertThat(result)
                .hasSameSizeAs(productDtos)
                .usingRecursiveComparison()
                .isEqualTo(productDtos);

        verify(productRepository).findAll();
        verify(productDtoMapper).mapToProductDtos(products);
    }

    @Test
    void getAllProductsWhenNoProductsAvailable_ThenThrowNoDataAvailableException() {
        assertThatThrownBy(() -> testee.getAllProducts())
                .isExactlyInstanceOf(NoDataAvailableException.class)
                .hasMessage("Shop is empty");

        verify(productRepository).findAll();
        verifyNoInteractions(productDtoMapper);
    }

    @Test
    void findProduct_WhenProductExist_ThenReturnProduct() {
        Long productId = 6L;
        Product product = Product.builder().build();
        when(productRepository.getById(productId)).thenReturn(product);
        ProductDto productDto = buildProductDto(productId, "Name6", "600", "Desc6");
        when(productDtoMapper.mapToProductDto(product)).thenReturn(productDto);

        ProductDto result = testee.findProduct(productId);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(productDto);

        verify(productRepository).getById(productId);
        verify(productDtoMapper).mapToProductDto(product);
    }

    @Test
    void findProduct_WhenProductNotExist_ThenThrowNoDataAvailableException() {
        Long productId = 7L;
        when(productRepository.getById(productId)).thenThrow(new NoDataAvailableException("Message"));

        assertThatThrownBy(() -> testee.findProduct(productId))
                .isExactlyInstanceOf(NoDataAvailableException.class)
                .hasMessage("Message");

        verify(productRepository).getById(productId);
        verifyNoInteractions(productDtoMapper);
    }

    @Test
    void addProduct_WhenProductNotExits_ThenAddNewProduct() {
        ProductDto productDto = buildProductDto(null, "Product8", "800", "Desc8");
        when(productRepository.existsByNameAndPriceAndDescription(
                productDto.getName(),
                productDto.getPrice(),
                productDto.getDescription())
        ).thenReturn(true);
        Product product = buildProductFromDto(productDto);
        when(productMapper.mapToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
    }

    private List<ProductDto> mockProducts() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(value -> buildProductDto(
                        (long) value,
                        "Product" + value,
                        value + "00",
                        "Desc" + value)
                )
                .toList();
    }

    private ProductDto buildProductDto(Long id,
                                       String name,
                                       String price,
                                       String description) {
        return ProductDto.builder()
                .setId(id)
                .setName(name)
                .setPrice(new BigDecimal(price))
                .setDescription(description)
                .build();
    }

    private Product buildProductFromDto(ProductDto productDto) {
        return Product.builder()
                .setId(productDto.getId())
                .setName(productDto.getName())
                .setPrice(productDto.getPrice())
                .setDescription(productDto.getDescription())
                .build();
    }

}
