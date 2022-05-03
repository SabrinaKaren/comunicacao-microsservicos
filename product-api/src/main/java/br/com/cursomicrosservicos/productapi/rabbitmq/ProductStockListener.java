package br.com.cursomicrosservicos.productapi.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import br.com.cursomicrosservicos.productapi.dto.product.ProductStockDto;
import br.com.cursomicrosservicos.productapi.services.ProductService;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void recieveProductStockMessage(ProductStockDto productStockDto) throws JsonProcessingException {
        log.info(
            "Receiving message with data: {}",
            new ObjectMapper().writeValueAsString(productStockDto)
        );
        productService.updateProductStock(productStockDto);
    }
    
}