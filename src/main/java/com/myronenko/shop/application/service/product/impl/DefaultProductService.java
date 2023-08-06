package com.myronenko.shop.application.service.product.impl;

import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.domain.entity.Product;
import com.myronenko.shop.application.exception.DataAlreadyExistsException;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import com.myronenko.shop.application.mapper.ProductDtoMapper;
import com.myronenko.shop.application.mapper.ProductMapper;
import com.myronenko.shop.application.repository.ProductRepository;
import com.myronenko.shop.application.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) {
            throw new NoDataAvailableException("Shop is empty");
        }
        return productDtoMapper.mapToProductDtos(allProducts);
    }

    @Override
    public ProductDto findProduct(Long productId) {
        Product product = productRepository.getById(productId);
        return productDtoMapper.mapToProductDto(product);
    }

    @Override
    public ProductDto addProduct(ProductDto productInfo) {
        checkProductAlreadyExist(productInfo);
        Product productToSave = productMapper.mapToProduct(productInfo);
        Product savedProduct = productRepository.save(productToSave);
        LOGGER.debug("Product added [{}]", savedProduct);
        return productDtoMapper.mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Long productId = productDto.getId();
        if (!productRepository.existsById(productId)) {
            throw new NoDataAvailableException("Product with id = [{0}] not exists", productId);
        }
        checkProductAlreadyExist(productDto);
        Product updatedProduct = productMapper.mapToProduct(productDto);
        productRepository.save(updatedProduct);
        LOGGER.debug("Product updated [{}]", updatedProduct);
        return productDto;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
        LOGGER.debug("Product with [{}] deleted", product);
    }

    private void checkProductAlreadyExist(ProductDto productDto) {
        String name = productDto.getName();
        BigDecimal price = productDto.getPrice();
        String description = productDto.getDescription();
        if (productRepository.existsByNameAndPriceAndDescription(name, price, description)) {
            LOGGER.info("Product with name = [{}], price = [{}] and description = [{}] already exists", name, price, description);
            throw new DataAlreadyExistsException("Duplicates are not permitted");
        }
    }

}
