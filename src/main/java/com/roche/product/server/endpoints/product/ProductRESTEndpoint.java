package com.roche.product.server.endpoints.product;

import com.roche.product.server.domains.product.Product;
import com.roche.product.server.domains.product.ProductNotFoundException;
import com.roche.product.server.domains.product.ProductService;
import com.roche.product.server.dtos.product.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/products"}, produces = APPLICATION_JSON_VALUE)
@Api(value = "products", description = "products management")
public class ProductRESTEndpoint {

    private ProductService productService;


    private static final String ID = "productId";

    public ProductRESTEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Get all products", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = new ArrayList<>();
        productService.getAllProducts().forEach(product ->
                products.add(convertToDto(product))
        );
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @ApiOperation(value = "Crate a new product", response = ProductDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product is created successfully"),
            @ApiResponse(code = 400, message = "The product to create is not valid")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createNewProduct(@Valid @RequestBody ProductDto newProduct) {
        final Product createdProduct = productService.createNewProduct(convertToEntity(newProduct));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdProduct));
    }

    @ApiOperation(value = "Update an product by its id", response = ProductDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is updated successfully "),
            @ApiResponse(code = 404, message = "Updated failed : The product id to update is not found"),
            @ApiResponse(code = 400, message = "Updated failed : The product to update is not valid"),
    })
    @PutMapping(path = "/{productId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateProduct(
            @Valid @RequestBody ProductDto newProduct,
            @PathVariable(value = ID) Long id) throws ProductNotFoundException {
        final Product updatedProduct = productService.updateProduct(convertToEntity(newProduct), id);
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedProduct));
    }

    @ApiOperation(value = "Delete an product by its id", response = ProductDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product is deleted successfully "),
            @ApiResponse(code = 404, message = "Deleted failed : The product id to delete is not found")
    })
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
