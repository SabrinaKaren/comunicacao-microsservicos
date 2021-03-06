package br.com.cursomicrosservicos.productapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;
import br.com.cursomicrosservicos.productapi.dto.category.CategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public static Category of(CategoryRequest categoryRequest) {
        Category response = new Category();
        BeanUtils.copyProperties(categoryRequest, response);
        return response;
    }
    
}