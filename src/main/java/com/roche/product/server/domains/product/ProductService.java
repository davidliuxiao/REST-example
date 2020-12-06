package com.roche.product.server.domains.product;

public interface ProductService {

    /**
     * Create a new product
     *
     * @param product to be create
     * @return the product created
     */
    Product createNewProduct(Product product);

    /**
     * Get all the products
     *
     * @return an iterable of all the products
     */
    Iterable<Product> getAllProducts();

    /**
     * Update the product by Id by the newProduct provider
     * @param newProduct
     * @param id
     * @return
     * @throws ProductNotFoundException
     */
    Product updateProduct(Product newProduct, Long id);

    /**
     * @param id
     */
    Product deleteProductById(Long id);
}
