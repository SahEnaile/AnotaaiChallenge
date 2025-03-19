package com.sarah.anotaai.services;

import com.sarah.anotaai.domain.category.CategoryDTO;
import com.sarah.anotaai.domain.category.CategoryResponseDTO;
import com.sarah.anotaai.domain.category.exceptions.CategoryNotFoundException;
import com.sarah.anotaai.repositories.CategoryRepository;
import com.sarah.anotaai.domain.category.Category;
import com.sarah.anotaai.services.aws.AwsSnsService;
import com.sarah.anotaai.services.aws.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public CategoryResponseDTO create(CategoryDTO categoryData) {
        Category newCategory = new Category();
        newCategory.setTitle(categoryData.title());
        newCategory.setDescription(categoryData.description());

        repository.save(newCategory);
        snsService.publish(new MessageDTO(newCategory.toString()));

        return CategoryResponseDTO.fromEntity(newCategory);
    }

    public List<CategoryResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
    }

    public CategoryResponseDTO getById(String id) {
        return repository.findById(id)
                .map(CategoryResponseDTO::fromEntity)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public CategoryResponseDTO update(String id, CategoryDTO categoryData) {
        Category category = repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if (StringUtils.hasText(categoryData.title())) {
            category.setTitle(categoryData.title());
        }
        if (StringUtils.hasText(categoryData.description())) {
            category.setDescription(categoryData.description());
        }

        repository.save(category);
        snsService.publish(new MessageDTO(category.toString()));

        return CategoryResponseDTO.fromEntity(category);
    }

    public void delete(String id) {
        Category category = repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        repository.delete(category);
    }
}
