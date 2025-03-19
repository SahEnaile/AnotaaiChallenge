package com.sarah.anotaai.services;

import com.sarah.anotaai.domain.category.CategoryResponseDTO;
import com.sarah.anotaai.domain.product.Product;
import com.sarah.anotaai.domain.product.ProductDTO;
import com.sarah.anotaai.domain.product.ProductResponseDTO;
import com.sarah.anotaai.domain.product.exceptions.ProductNotFoundException;
import com.sarah.anotaai.repositories.ProductRepository;
import com.sarah.anotaai.services.aws.AwsSnsService;
import com.sarah.anotaai.services.aws.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository repository;
    private final AwsSnsService snsService;

    // Criar produto e retornar DTO
    public ProductResponseDTO create(ProductDTO productData) {
        // Obtém a categoria diretamente como DTO
        CategoryResponseDTO category = categoryService.getById(productData.categoryId());

        Product newProduct = new Product(productData);
        repository.save(newProduct);

        snsService.publish(new MessageDTO(newProduct.toString()));

        return ProductResponseDTO.fromEntity(newProduct);
    }

    // Buscar todos os produtos e retornar uma lista de DTOs
    public List<ProductResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Buscar produto por ID e retornar DTO
    public ProductResponseDTO getById(String id) {
        return repository.findById(id)
                .map(ProductResponseDTO::fromEntity)
                .orElseThrow(ProductNotFoundException::new);
    }

    // Atualizar produto e retornar DTO atualizado
    public ProductResponseDTO update(String id, ProductDTO productData) {
        Product product = repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (StringUtils.hasText(productData.title())) {
            product.setTitle(productData.title());
        }
        if (StringUtils.hasText(productData.description())) {
            product.setDescription(productData.description());
        }
        if (productData.price() != null) {
            product.setPrice(productData.price());
        }
        if (productData.categoryId() != null) {
            categoryService.getById(productData.categoryId());
            product.setCategory(productData.categoryId());
        }

        repository.save(product);
        snsService.publish(new MessageDTO(product.toString()));

        return ProductResponseDTO.fromEntity(product);
    }

    // Deletar produto por ID
    public void delete(String id) {
        Product product = repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        repository.delete(product);
    }
}