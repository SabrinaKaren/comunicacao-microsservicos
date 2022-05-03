package br.com.cursomicrosservicos.productapi.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import br.com.cursomicrosservicos.productapi.dto.sale.SaleConfirmationDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SaleConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SaleConfirmationDto saleConfirmationDto) {
        try {
            log.info("Sending message: {}", new ObjectMapper().writeValueAsString(saleConfirmationDto));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, saleConfirmationDto);
            log.info("Message was sent successfully!");
        } catch (Exception e) {
            log.info("Error while trying to send sales confirmation message: ", e);
        }
    }
    
}