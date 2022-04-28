package br.com.cursomicrosservicos.productapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cursomicrosservicos.productapi.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}