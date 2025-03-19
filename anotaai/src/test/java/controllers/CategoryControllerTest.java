package controllers;

import com.sarah.anotaai.controllers.CategoryController;
import com.sarah.anotaai.domain.category.CategoryDTO;
import com.sarah.anotaai.domain.category.CategoryResponseDTO;
import com.sarah.anotaai.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO categoryDTO;
    private CategoryResponseDTO categoryResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryDTO = new CategoryDTO("title", "description", "test-owner-id"); // Adicionando ownerId
        categoryResponseDTO = new CategoryResponseDTO("1", "title", "description");
    }

    @Test
    void testCreateCategory() {
        when(categoryService.create(any(CategoryDTO.class))).thenReturn(categoryResponseDTO);

        ResponseEntity<CategoryResponseDTO> response = categoryController.create(categoryDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue()); // Ajustado para Created (201)
        assertEquals(categoryResponseDTO.id(), response.getBody().id());
        verify(categoryService, times(1)).create(any(CategoryDTO.class));
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAll()).thenReturn(List.of(categoryResponseDTO));

        ResponseEntity<List<CategoryResponseDTO>> response = categoryController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(categoryResponseDTO.id(), response.getBody().get(0).id());
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void testUpdateCategory() {
        when(categoryService.update(anyString(), any(CategoryDTO.class))).thenReturn(categoryResponseDTO);

        ResponseEntity<CategoryResponseDTO> response = categoryController.update("1", categoryDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoryResponseDTO.id(), response.getBody().id());
        verify(categoryService, times(1)).update(anyString(), any(CategoryDTO.class));
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).delete(anyString());

        ResponseEntity<Void> response = categoryController.delete("1");

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(categoryService, times(1)).delete("1"); // Garante que o método foi chamado corretamente
    }
}
