package com.myronenko.shop.application.mapper;

import com.myronenko.shop.application.domain.dto.ProductDto;
import com.myronenko.shop.application.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductDtoMapper {

    ProductDto mapToProductDto(Product product);

    List<ProductDto> mapToProductDtos(List<Product> products);

}
