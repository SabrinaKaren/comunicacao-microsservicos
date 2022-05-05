package br.com.cursomicrosservicos.productapi.dto.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockDto {

    private String salesId;
    private List<ProductQuantityDto> products;
    private String transactionid;
    
}