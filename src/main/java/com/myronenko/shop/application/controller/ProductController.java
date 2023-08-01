package com.myronenko.shop.application.controller;

import com.myronenko.shop.application.controller.api.Api;
import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Simple rest controller with endpoints for CRUD operations
 */

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(Api.BASE_URL + "/shop/products")
public class ProductController {

    private static final String ID_ROUTE = "/{id}";

    private ProductService productService;

    @Operation(summary = "Get all products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by id")
    @GetMapping(value = ID_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProduct(@Positive
                                 @RequestParam
                                 @PathVariable("id") Long productId) {
        return productService.findProduct(productId);
    }

    @Operation(summary = "Create product and save in db")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto createProduct(@Valid
                                    @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @Operation(summary = "Update existing product")
    @PutMapping(value = ID_ROUTE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@Positive
                                    @PathVariable Long id,
                                    @Valid
                                    @RequestBody ProductDto productDto) {
        productDto.setId(id);
        return productService.updateProduct(productDto);
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping(value = ID_ROUTE)
    public void deleteProduct(@Positive
                              @RequestParam
                              @PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
