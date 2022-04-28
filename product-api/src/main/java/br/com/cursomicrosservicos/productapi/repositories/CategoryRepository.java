package br.com.cursomicrosservicos.productapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cursomicrosservicos.productapi.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}