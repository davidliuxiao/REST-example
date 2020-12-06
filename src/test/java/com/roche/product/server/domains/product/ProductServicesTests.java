package com.roche.product.server.domains.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ProductService}
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServicesTests {

    LocalDate date = LocalDate.of(2020, 12, 6);
    private final long productId = 1l;
    private final long productIdNonExist = 2l;
    private final Product product1 = new Product("name1", new BigDecimal(20.0), date);
    private final Product product2 = new Product("name2", new BigDecimal(20.0), date);

    private String productNonExistError = "Product " + productIdNonExist + " does not exists";

    @Mock
    private Products products;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        when(products.save(any(Product.class))).then(AdditionalAnswers.returnsFirstArg());
        productService = new ProductServiceImpl(products);
    }

    @Test
    public void testGetAllProduct() {
        givenOneProductExist();

        Iterable<Product> expectedProducts = products.findAll();
        Iterable<Product> actualProducts = productService.getAllProducts();

        assertThat(actualProducts).isEqualTo(expectedProducts);
    }

    @Test
    public void testCreateNewProduct() {
        Product expectedProduct = products.save(product1);
        Product actualProduct = productService.createNewProduct(product1);

        assertThat(actualProduct).isEqualTo(expectedProduct);
    }


    @Test
    public void testSuccessfullyUpdateExistProduct() {
        givenOneProductExist();
        Product expectedProduct = products.save(product2);
        Product actualProduct = productService.updateProduct(product2, productId);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void testFailToUpdateNonExistProduct() {
        givenOneProductExist();
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product1, productIdNonExist));
        assertEquals(productNonExistError, exception.getMessage());
    }

    @Test
    public void testSuccessfullyDeleteExistProduct() {
        givenOneProductExist();
        Long productId = 1L;
        Product expectedProduct = products.deleteById(productId).get();
        Product actualProduct = productService.deleteProductById(productId);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void testFailToDeleteNonExistProduct() {
        givenOneProductExist();
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(productIdNonExist));
        assertEquals(productNonExistError, exception.getMessage());
    }


    private void givenOneProductExist() {
        when(products.findAll()).thenReturn(Collections.singletonList(product1));
        when(products.findById(productId)).thenReturn(Optional.of(product1));
        when(products.deleteById(productId)).thenReturn(Optional.of(product1));
    }
}
