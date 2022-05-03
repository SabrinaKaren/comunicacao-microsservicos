package br.com.cursomicrosservicos.productapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {

    private Integer productId;
    private Integer quantity;
    
}