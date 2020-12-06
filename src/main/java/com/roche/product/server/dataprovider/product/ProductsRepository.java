package com.roche.product.server.dataprovider.product;

import com.roche.product.server.domains.product.Product;
import com.roche.product.server.domains.product.Products;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class ProductsRepository implements Products {

    private final ProductsJpaRepository productsJpaRepository;

    public ProductsRepository(ProductsJpaRepository productsJpaRepository) {
        this.productsJpaRepository = productsJpaRepository;
    }

    @Override
    public Product save(Product product) {
        return productsJpaRepository.save(product);
    }

    @Override
    public Iterable<Product> findAll() {
        return productsJpaRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productsJpaRepository.findById(id);
    }

    @Override
    public Optional<Product> deleteById(Long id) {
        Optional<Product> productToDeleted =  productsJpaRepository.findById(id);
        productToDeleted.ifPresent(product ->  productsJpaRepository.deleteById(product.getId()));
        return productToDeleted;
    }
}
