package br.com.cursomicrosservicos.productapi.services;

import java.util.List;
import java.util.stream.Collectors;
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
        if (id == null) {
            throw new ExceptionValidation("The id must be informed.");
        }

        return categoryRepository
            .findById(id)
            .orElseThrow(() -> new ExceptionValidation("There's no category for the given ID."));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository
            .findAll()
            .stream()
            .map(CategoryResponse::of)
            .collect(Collectors.toList());
    }

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findByDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new ExceptionValidation("The description must be informed.");
        }

        return categoryRepository
            .findByDescriptionIgnoreCaseContaining(description)
            .stream()
            .map(CategoryResponse::of)
            .collect(Collectors.toList());
    }

    /* Validadores */

    private void validateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null || categoryRequest.getDescription() == null || categoryRequest.getDescription().isEmpty()) {
            throw new ExceptionValidation("The category description was not informed.");
        }
    }
    
}