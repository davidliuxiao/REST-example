package com.roche.product.server.endpoints.product;

import com.roche.product.server.domains.product.Product;
import com.roche.product.server.domains.product.ProductNotFoundException;
import com.roche.product.server.domains.product.ProductService;
import com.roche.product.server.dtos.product.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/products"}, produces = APPLICATION_JSON_VALUE)
public class ProductRESTEndpoint {

    private ProductService productService;


    private static final String ID = "productId";

    public ProductRESTEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = new ArrayList<>();
        productService.getAllProducts().forEach(product ->
                products.add(convertToDto(product))
        );
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createNewProduct(@Valid @RequestBody ProductDto newProduct) {
        final Product createdProduct = productService.createNewProduct(convertToEntity(newProduct));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdProduct));
    }

    @PutMapping(path = "/{productId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(
            @Valid @RequestBody ProductDto newProduct,
            @PathVariable(value = ID) Long id) throws ProductNotFoundException {
        final Product updatedProduct = productService.updateProduct(convertToEntity(newProduct), id);
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedProduct));
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable(value = ID) Long id) throws ProductNotFoundException {
        final Product deletedProduct = productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(deletedProduct));
    }


    private Product convertToEntity(ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getPrice(), productDto.getDate());
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDate());
    }
}
