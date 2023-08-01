package com.myronenko.shop.application.service.product;

import com.myronenko.shop.application.domain.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto findProduct(Long productId);

    ProductDto addProduct(ProductDto productInfo);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(Long id);

}
