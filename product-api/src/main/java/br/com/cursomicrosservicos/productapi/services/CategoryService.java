package br.com.cursomicrosservicos.productapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.dto.CategoryRequest;
import br.com.cursomicrosservicos.productapi.dto.CategoryResponse;
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

    private void validateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null || categoryRequest.getDescription() == null || categoryRequest.getDescription().isEmpty()) {
            throw new ExceptionValidation("The category descrption was not informed.");
        }
    }
    
}