package com.roche.product.server.dataprovider.product;

import com.roche.product.server.dataprovider.SoftDeleteCrudRepository;
import com.roche.product.server.domains.product.Product;

interface ProductsJpaRepository extends SoftDeleteCrudRepository<Product, Long> {
}
