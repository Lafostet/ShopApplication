package com.myronenko.shop.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myronenko.shop.application.controller.api.Api;
import com.myronenko.shop.application.domain.dto.ErrorResponseDto;
import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import com.myronenko.shop.application.service.product.ProductService;
import com.myronenko.shop.application.service.util.DateTimeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductController.class,
        ErrorController.class
})
class ProductControllerTest {

    private static final String PRODUCTS_URL = "/shop/products";
    private static final String PRODUCTS_REQUEST_URL = Api.BASE_URL + PRODUCTS_URL;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private DateTimeGenerator dateTimeGenerator;

    @Test
    void getProducts_WhenRequested_ThenHttpStatusOkReturned() throws Exception {
        ProductDto productDto = ProductDto.builder().build();
        List<ProductDto> response = List.of(productDto);
        when(productService.getAllProducts()).thenReturn(response);

        String responseJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS_REQUEST_URL)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    void getProducts_WhenNoDataAvailable_ThenHttpStatusNotFoundReturned() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2023, 7, 29, 12, 12);
        when(dateTimeGenerator.generateNow()).thenReturn(dateTime);
        NoDataAvailableException exception = mock(NoDataAvailableException.class);
        when(exception.getMessage()).thenReturn("empty");
        when(productService.getAllProducts()).thenThrow(exception);

        ErrorResponseDto responseDto = new ErrorResponseDto("empty", "no_data", dateTime);
        String responseJson = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS_REQUEST_URL)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(responseJson));
    }

    @Test
    void getProduct_WhenProductExist_ThenHttpStatusOkReturned() throws Exception {
        Long productId = 1L;
        ProductDto response = ProductDto.builder().build();
        when(productService.findProduct(productId)).thenReturn(response);

        String responseJson = objectMapper.writeValueAsString(response);

        String productIdStr = "1";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS_REQUEST_URL + "/{id}", productIdStr)
                        .param("productId", productIdStr)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

}