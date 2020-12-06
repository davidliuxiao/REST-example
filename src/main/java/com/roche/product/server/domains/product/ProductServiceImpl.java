package com.roche.product.server.domains.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final Products products;

    ProductServiceImpl(Products products) {
        this.products = products;
    }

    @Override
    public Product createNewProduct(Product product) {
        Product productCreated = products.save(product);
        logger.info("Product {} is create : {}", product.getId(), product);
        return productCreated;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return products.findAll();
    }

    @Override
    public Product updateProduct(Product newProduct, Long id) {
        return products.findById(id)
                .map(product -> {
                            product.setDate(newProduct.getDate());
                            product.setName(newProduct.getName());
                            product.setPrice(newProduct.getPrice());
                            logger.info("Product {} is updated : {}", id, product);
                            return products.save(product);
                        }

                ).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product deleteProductById(Long id) {
        return products.deleteById(id)
                .map(product -> {
                            logger.info("Product {} is deleted : {}", id, product);
                            return product;
                        }

                ).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
