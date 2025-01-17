package com.sarah.anotaai.services;

import com.sarah.anotaai.domain.category.Category;
import com.sarah.anotaai.domain.category.CategoryDTO;
import com.sarah.anotaai.domain.category.exceptions.CategoryNotFoundException;
import com.sarah.anotaai.domain.product.Product;
import com.sarah.anotaai.domain.product.ProductDTO;
import com.sarah.anotaai.domain.product.exceptions.ProductNotFoundException;
import com.sarah.anotaai.repositories.CategoryRepository;
import com.sarah.anotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;

    private ProductRepository repository;

    public ProductService(ProductRepository  productRepository, CategoryService categoryService){
        this.repository = productRepository;
        this.categoryService = categoryService;
    }
    public Product create(ProductDTO productData){
        Category category = this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        return newProduct;
    }

    public List<Product> getAll(){
        return this.repository.findAll();
    }

    public Product update(String id, ProductDTO productData){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null){
            this.categoryService.getById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }

        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);

        return product;
    }
    public void delete(String id){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(product);
    }
}
