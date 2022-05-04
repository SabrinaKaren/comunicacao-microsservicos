package br.com.cursomicrosservicos.productapi.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryResponse;
import br.com.cursomicrosservicos.productapi.dto.supplier.SupplierResponse;
import br.com.cursomicrosservicos.productapi.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleResponse {

    private Integer id;
    private String name;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private Integer quantityAvailable;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    private List<String> sales;

    public static ProductSaleResponse of(Product product, List<String> sales) {
        return ProductSaleResponse
            .builder()
            .id(product.getId())
            .name(product.getName())
            .supplier(SupplierResponse.of(product.getSupplier()))
            .category(CategoryResponse.of(product.getCategory()))
            .quantityAvailable(product.getQuantityAvailable())
            .createdAt(product.getCreatedAt())
            .sales(sales)
            .build();
    }
    
}