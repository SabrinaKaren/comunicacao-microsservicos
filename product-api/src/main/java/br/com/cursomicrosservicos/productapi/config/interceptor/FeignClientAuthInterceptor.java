package br.com.cursomicrosservicos.productapi.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import static br.com.cursomicrosservicos.productapi.config.RequestUtil.getCurrentRequest;

@Component
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Override
    public void apply(RequestTemplate template) {
        var currentRequest = getCurrentRequest();
        template
            .header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION))
            .header(TRANSACTION_ID, currentRequest.getHeader(TRANSACTION_ID));
    }
    
}