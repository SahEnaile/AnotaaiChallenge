package controllers;

import com.sarah.anotaai.controllers.ProductController;
import com.sarah.anotaai.domain.product.Product;
import com.sarah.anotaai.domain.product.ProductDTO;
import com.sarah.anotaai.services.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productDTO = new ProductDTO("productTitle", "productDescription", "ownerId", 100, "categoryId");
        product = new Product(productDTO);
        product.setId("1");
    }

    @Test
    void testCreateProduct() {
        when(productService.create(any(ProductDTO.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.create(productDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product.getId(), response.getBody().getId());
        verify(productService, times(1)).create(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAll()).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = productController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(productService, times(1)).getAll();
    }

    @Test
    void testUpdateProduct() {
        when(productService.update(anyString(), any(ProductDTO.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.update("1", productDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product.getId(), response.getBody().getId());
        verify(productService, times(1)).update(anyString(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).delete(anyString());

        ResponseEntity<Product> response = productController.delete("1");

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).delete(anyString());
    }
}
