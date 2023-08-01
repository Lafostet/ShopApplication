package com.myronenko.shop.application.repository;

import com.myronenko.shop.application.domain.entity.Product;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameAndPriceAndDescription(String name, BigDecimal price, String description);

    default Product getById(Long id) {
        return findById(id).orElseThrow(() -> new NoDataAvailableException(MessageFormat.format("Product with id = [{0}] not exist", id)));
    }

}
