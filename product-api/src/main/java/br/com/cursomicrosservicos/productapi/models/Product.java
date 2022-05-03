package br.com.cursomicrosservicos.productapi.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.cursomicrosservicos.productapi.dto.product.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY", nullable = false)
    private Category category;

    @Column(name = "QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    public static Product of(ProductRequest productRequest, Category category, Supplier supplier) {
        return Product
            .builder()
            .name(productRequest.getName())
            .quantityAvailable(productRequest.getQuantityAvailable())
            .category(category)
            .supplier(supplier)
            .build();
    }

    public void downStock(Integer quantity) {
        this.quantityAvailable = this.quantityAvailable - quantity;
    }
    
}