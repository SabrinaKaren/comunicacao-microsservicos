package br.com.cursomicrosservicos.productapi.dto.sale;

import br.com.cursomicrosservicos.productapi.enums.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleConfirmationDto {

    private String salesId;
    private SaleStatus status;
    
}