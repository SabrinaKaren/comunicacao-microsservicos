package br.com.cursomicrosservicos.productapi.dto;

import org.springframework.beans.BeanUtils;
import br.com.cursomicrosservicos.productapi.models.Category;
import lombok.Data;

@Data
public class CategoryResponse {

    private Integer id;
    private String description;

    public static CategoryResponse of(Category category) {
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }
    
}