package controllers;

import com.sarah.anotaai.controllers.ProductController;
import com.sarah.anotaai.domain.product.ProductDTO;
import com.sarah.anotaai.domain.product.ProductResponseDTO;
import com.sarah.anotaai.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;
    private ProductResponseDTO productResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productDTO = new ProductDTO("productTitle", "productDescription", "ownerId", 100, "categoryId");
        productResponseDTO = new ProductResponseDTO("1", "productTitle", "productDescription", 100, "categoryId");
    }

    @Test
    void testCreateProduct() {
        when(productService.create(any(ProductDTO.class))).thenReturn(productResponseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.create(productDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponseDTO.id(), response.getBody().id());
        verify(productService, times(1)).create(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAll()).thenReturn(List.of(productResponseDTO));

        ResponseEntity<List<ProductResponseDTO>> response = productController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(productResponseDTO.id(), response.getBody().get(0).id());
        verify(productService, times(1)).getAll();
    }

    @Test
    void testUpdateProduct() {
        when(productService.update(anyString(), any(ProductDTO.class))).thenReturn(productResponseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.update("1", productDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponseDTO.id(), response.getBody().id());
        verify(productService, times(1)).update(anyString(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).delete(anyString());

        ResponseEntity<Void> response = productController.delete("1");

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).delete(anyString());
    }
}
