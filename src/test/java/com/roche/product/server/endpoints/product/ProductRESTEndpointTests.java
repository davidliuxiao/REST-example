package com.roche.product.server.endpoints.product;

import com.roche.product.server.domains.product.Product;
import com.roche.product.server.domains.product.ProductNotFoundException;
import com.roche.product.server.domains.product.ProductService;
import com.roche.product.server.dtos.product.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link ProductRESTEndpoint}
 */

@WebMvcTest(ProductRESTEndpoint.class)
@AutoConfigureJsonTesters
public class ProductRESTEndpointTests {

    private final String API_PATH = "/api/v1/products";

    private final long productId = 1l;
    private final long productIdNonExist = 3l;
    private final String PRODUCT_ID = "/" + productId;
    private final String PRODUCT_ID_NON_EXIST = "/" + productIdNonExist;
    private final LocalDate date = LocalDate.of(2020, 12, 6);


    private String productNonExistError = "Product " + productIdNonExist + " does not exists";

    ProductDto productDto = new ProductDto(productId, "name1", new BigDecimal(20.0), date);
    ProductDto updatedProductDto = new ProductDto(productId, "name2", new BigDecimal(30.0), date);
    Product product = new Product("name1", new BigDecimal(20.0), date);
    Product mappedProduct = new Product(1l,"name1", new BigDecimal(20.0), date);
    Product toUpdateProduct = new Product("name2", new BigDecimal(30.0), date);
    Product updatedProduct = new Product(1l,"name2", new BigDecimal(30.0), date);

    private MockMvc mockMvc;


    @Autowired
    @SuppressWarnings("raw-type")
    private JacksonTester jsonProduct;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        ProductRESTEndpoint productRESTEndpoint = new ProductRESTEndpoint(productService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productRESTEndpoint)
                .setControllerAdvice(new GeneralExceptionHandler())
                .build();
    }

    @Test
    public void testGetProductsSuccess() throws Exception {
        givenOneProductExist();
        this.mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(jsonProduct.write(Collections.singletonList(productDto)).getJson()));
    }


    @Test
    public void deleteProductSuccess() throws Exception {
        givenOneProductExist();
        this.mockMvc.perform(delete(API_PATH + PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(jsonProduct.write(productDto).getJson()));
    }

    @Test
    public void updateProductSuccess() throws Exception {
        givenOneProductExist();
        this.mockMvc.perform(put(API_PATH + PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct.write(updatedProductDto).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(jsonProduct.write(updatedProductDto).getJson()));
    }

    @Test
    public void createNewProduct() throws Exception {
        givenOneProductExist();
        this.mockMvc.perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct.write(productDto).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(jsonProduct.write(productDto).getJson()));
    }

    @Test
    public void deleteProductFail() throws Exception {
        givenDeleteNonExistProductThrowsException();
        this.mockMvc.perform(delete(API_PATH + PRODUCT_ID_NON_EXIST))
                .andExpect(status().isNoContent())
                .andExpect(content().string(productNonExistError));
    }

    @Test
    public void updateProductFail() throws Exception {
        givenUpdateNonExistProductThrowsException();
        this.mockMvc.perform(put(API_PATH + PRODUCT_ID_NON_EXIST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct.write(updatedProductDto).getJson()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(productNonExistError));
    }


    private void givenUpdateNonExistProductThrowsException() {
        when(productService.updateProduct(toUpdateProduct, productIdNonExist)).thenThrow(new ProductNotFoundException(productIdNonExist));
    }

    private void givenDeleteNonExistProductThrowsException() {
        when(productService.deleteProductById(productIdNonExist)).thenThrow(new ProductNotFoundException(productIdNonExist));
    }

    private void givenOneProductExist() {
        when(productService.createNewProduct(product)).thenReturn(mappedProduct);
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(mappedProduct));
        when(productService.deleteProductById(productId)).thenReturn(mappedProduct);
        when(productService.updateProduct(toUpdateProduct, productId)).thenReturn(updatedProduct);
    }

}

