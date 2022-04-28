package br.com.cursomicrosservicos.productapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.dto.product.ProductRequest;
import br.com.cursomicrosservicos.productapi.dto.product.ProductResponse;
import br.com.cursomicrosservicos.productapi.models.Category;
import br.com.cursomicrosservicos.productapi.models.Product;
import br.com.cursomicrosservicos.productapi.models.Supplier;
import br.com.cursomicrosservicos.productapi.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    public ProductResponse save(ProductRequest productRequest) {
        validateProduct(productRequest);
        Category category = categoryService.findById(productRequest.getCategoryId());
        Supplier supplier = supplierService.findById(productRequest.getSupplierId());

        Product productSaved = productRepository.save(Product.of(productRequest, category, supplier));
        return ProductResponse.of(productSaved);
    }

    /* Validadores */

    private void validateProduct(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new ExceptionValidation("The product object was not informed.");
        }
        this.validateProductName(productRequest);
        this.validateProductQuantityAvailable(productRequest);
        this.validateProductCategoryIdAndSupplierId(productRequest);
    }

    private void validateProductName(ProductRequest productRequest) {
        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new ExceptionValidation("The product name was not informed.");
        }
    }

    private void validateProductQuantityAvailable(ProductRequest productRequest) {
        if (productRequest.getQuantityAvailable() == null) {
            throw new ExceptionValidation("The product quantity was not informed.");
        }
        if (productRequest.getQuantityAvailable() <= 0) {
            throw new ExceptionValidation("The product quantity should not be less or equal to zero.");
        }
    }

    private void validateProductCategoryIdAndSupplierId(ProductRequest productRequest) {
        if (productRequest.getCategoryId() == null) {
            throw new ExceptionValidation("The product category was not informed.");
        }
        if (productRequest.getSupplierId() == null) {
            throw new ExceptionValidation("The product supplier was not informed.");
        }
    }
    
}