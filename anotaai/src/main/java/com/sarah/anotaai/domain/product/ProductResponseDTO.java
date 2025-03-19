package com.sarah.anotaai.domain.product;

public record ProductResponseDTO(
        String id,
        String title,
        String description,
        Integer price,
        String categoryId
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }
}
