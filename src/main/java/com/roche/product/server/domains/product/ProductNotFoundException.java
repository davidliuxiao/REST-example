package com.roche.product.server.domains.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product " + id + " does not exists");
    }
}
