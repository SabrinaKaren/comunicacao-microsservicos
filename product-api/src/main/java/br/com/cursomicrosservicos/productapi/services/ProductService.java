package br.com.cursomicrosservicos.productapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.config.success.SuccessResponse;
import br.com.cursomicrosservicos.productapi.dto.product.ProductQuantityDto;
import br.com.cursomicrosservicos.productapi.dto.product.ProductRequest;
import br.com.cursomicrosservicos.productapi.dto.product.ProductResponse;
import br.com.cursomicrosservicos.productapi.dto.product.ProductStockDto;
import br.com.cursomicrosservicos.productapi.dto.sale.SaleConfirmationDto;
import br.com.cursomicrosservicos.productapi.enums.SaleStatus;
import br.com.cursomicrosservicos.productapi.models.Category;
import br.com.cursomicrosservicos.productapi.models.Product;
import br.com.cursomicrosservicos.productapi.models.Supplier;
import br.com.cursomicrosservicos.productapi.rabbitmq.SaleConfirmationSender;
import br.com.cursomicrosservicos.productapi.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SaleConfirmationSender saleConfirmationSender;

    public ProductResponse save(ProductRequest productRequest) {
        validateProduct(productRequest);
        Category category = categoryService.findById(productRequest.getCategoryId());
        Supplier supplier = supplierService.findById(productRequest.getSupplierId());

        Product productSaved = productRepository.save(Product.of(productRequest, category, supplier));
        return ProductResponse.of(productSaved);
    }

    public Product findById(Integer id) {
        validateInformedId(id);
        return productRepository
            .findById(id)
            .orElseThrow(() -> new ExceptionValidation("There's no product for the given ID."));
    }

    public List<ProductResponse> findAll() {
        return productRepository
            .findAll()
            .stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    public List<ProductResponse> findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ExceptionValidation("The name must be informed.");
        }

        return productRepository
            .findByNameIgnoreCaseContaining(name)
            .stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            throw new ExceptionValidation("The categoryId must be informed.");
        }

        return productRepository
            .findByCategoryId(categoryId)
            .stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if (supplierId == null) {
            throw new ExceptionValidation("The supplierId must be informed.");
        }

        return productRepository
            .findBySupplierId(supplierId)
            .stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted.");
    }

    public ProductResponse update(ProductRequest productRequest, Integer id) {
        validateInformedId(id);
        validateProduct(productRequest);

        Category category = categoryService.findById(productRequest.getCategoryId());
        Supplier supplier = supplierService.findById(productRequest.getSupplierId());
        Product product = Product.of(productRequest, category, supplier);
        product.setId(id);

        productRepository.save(product);
        return ProductResponse.of(product);
    }

    public void updateProductStock(ProductStockDto productStockDto) {

        try {
            validateStockUpdateData(productStockDto);
            updateStock(productStockDto);
        } catch (Exception e) {
            log.error("Error while trying to update stock for message with error: {}", e.getMessage(), e);

            // comunicar com o RabbitMQ
            SaleConfirmationDto rejectedMessage = new SaleConfirmationDto(productStockDto.getSalesId(), SaleStatus.REJECTED);
            saleConfirmationSender.sendSalesConfirmationMessage(rejectedMessage);
        }

    }

    @Transactional
    public void updateStock(ProductStockDto productStockDto) {
        List<Product> productsToUpdate = new ArrayList<Product>();

        productStockDto.getProducts().forEach(item -> {
            Product existingProduct = findById(item.getProductId());
            validateInformedId(item, existingProduct);

            existingProduct.downStock(item.getQuantity());
            productsToUpdate.add(existingProduct);
        });

        if (!productsToUpdate.isEmpty()) {
            productRepository.saveAll(productsToUpdate);

            // comunicar com o RabbitMQ
            SaleConfirmationDto approvedMessage = new SaleConfirmationDto(productStockDto.getSalesId(), SaleStatus.APPROVED);
            saleConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        }
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

    private void validateInformedId(Integer id) {
        if (id == null) {
            throw new ExceptionValidation("The id must be informed.");
        }
    }

    private void validateStockUpdateData(ProductStockDto productStockDto) {
        if (productStockDto == null || productStockDto.getSalesId() == null || productStockDto.getSalesId().isEmpty()) {
            throw new ExceptionValidation("The product data and the sales id must be informed.");
        }
        if (productStockDto.getProducts() == null || productStockDto.getProducts().isEmpty()) {
            throw new ExceptionValidation("The sales' product must be informed.");
        }
        productStockDto.getProducts().forEach(item -> {
            if (item.getQuantity() == null || item.getProductId() == null) {
                throw new ExceptionValidation("The product id and the quantity must be informed.");
            }
        });
    }

    private void validateInformedId(ProductQuantityDto productQuantityDto, Product existingProduct) {
        if (productQuantityDto.getQuantity() > existingProduct.getQuantityAvailable()) {
            throw new ExceptionValidation(String.format("The product %s is out of stock.", existingProduct.getId()));
        }
    }
    
}