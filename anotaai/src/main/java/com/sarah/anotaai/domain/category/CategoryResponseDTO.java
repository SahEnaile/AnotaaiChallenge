package com.sarah.anotaai.domain.category;

public record CategoryResponseDTO(String id, String title, String description) {
    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription()
        );
    }
}
