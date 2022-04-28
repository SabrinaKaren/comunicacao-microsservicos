package br.com.cursomicrosservicos.productapi.dto.supplier;

import org.springframework.beans.BeanUtils;
import br.com.cursomicrosservicos.productapi.models.Supplier;
import lombok.Data;

@Data
public class SupplierResponse {

    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }
    
}