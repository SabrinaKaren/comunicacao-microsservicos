package br.com.cursomicrosservicos.productapi.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.config.success.SuccessResponse;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryRequest;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryResponse;
import br.com.cursomicrosservicos.productapi.models.Category;
import br.com.cursomicrosservicos.productapi.repositories.CategoryRepository;
import br.com.cursomicrosservicos.productapi.repositories.ProductRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public CategoryResponse save(CategoryRequest categoryRequest) {
        validateCategory(categoryRequest);
        Category categorySaved = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(categorySaved);
    }

    public Category findById(Integer id) {
        validateInformedId(id);
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

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if (productRepository.existsByCategoryId(id)) {
            throw new ExceptionValidation("You cannot delete this category because it's already defined by a product.");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The category was deleted.");
    }

    public CategoryResponse update(CategoryRequest categoryRequest, Integer id) {
        validateInformedId(id);
        validateCategory(categoryRequest);

        Category category = Category.of(categoryRequest);
        category.setId(id);

        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    /* Validadores */

    private void validateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null || categoryRequest.getDescription() == null || categoryRequest.getDescription().isEmpty()) {
            throw new ExceptionValidation("The category description was not informed.");
        }
    }

    private void validateInformedId(Integer id) {
        if (id == null) {
            throw new ExceptionValidation("The id must be informed.");
        }
    }
    
}