package br.com.cursomicrosservicos.productapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryRequest;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryResponse;
import br.com.cursomicrosservicos.productapi.models.Category;
import br.com.cursomicrosservicos.productapi.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse save(CategoryRequest categoryRequest) {
        validateCategory(categoryRequest);
        Category categorySaved = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(categorySaved);
    }

    public Category findById(Integer id) {
        return categoryRepository
            .findById(id)
            .orElseThrow(() -> new ExceptionValidation("There's no category for the given ID."));
    }

    /* Validadores */

    private void validateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null || categoryRequest.getDescription() == null || categoryRequest.getDescription().isEmpty()) {
            throw new ExceptionValidation("The category description was not informed.");
        }
    }
    
}