package com.myronenko.shop.application.repository;

import com.myronenko.shop.application.domain.entity.Product;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.MessageFormat;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameAndPriceAndDescription(String name, BigDecimal price, String description);

    default Product getById(@NotNull Long id) {
        return findById(id).orElseThrow(() -> new NoDataAvailableException(MessageFormat.format("Product with id = [{0}] not exist", id)));
    }

}
