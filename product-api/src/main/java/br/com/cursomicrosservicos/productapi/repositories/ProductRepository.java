package br.com.cursomicrosservicos.productapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cursomicrosservicos.productapi.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findByCategoryId(Integer id);

    List<Product> findBySupplierId(Integer id);
    
}