package br.com.cursomicrosservicos.productapi.clients;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.cursomicrosservicos.productapi.dto.sale.SaleProductResponse;

@FeignClient(
    name = "saleClient",
    contextId = "saleClient",
    url = "${app-config.services.sales}"
)
public interface SaleClient {

    @GetMapping("/api/orders/product/{productId}")
    Optional<SaleProductResponse> findSalesByProductId(@PathVariable Integer productId);
    
}