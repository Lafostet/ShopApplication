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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
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
        LOGGER.info("Product added [{}]", savedProduct);
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
        productRepository.deleteById(id);
        LOGGER.info("Product with id = [{}] deleted", id);
    }

    private void checkProductAlreadyExist(ProductDto productDto) {
        String name = productDto.getName();
        BigDecimal price = productDto.getPrice();
        String description = productDto.getDescription();
        if (productRepository.findByNameAndPriceAndDescription(name, price, description).isPresent()) {
            throw new DataAlreadyExistsException("Product with name = [{0}], price = [{1}] and description = [{2}] already exists", name, price, description);
        }
    }

}
