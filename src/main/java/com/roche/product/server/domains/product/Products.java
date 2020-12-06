package com.roche.product.server.domains.product;

import java.util.Optional;

public interface Products {
    Product save(Product product);

    Iterable<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> deleteById(Long id);
}
