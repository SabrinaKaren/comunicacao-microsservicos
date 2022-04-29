package br.com.cursomicrosservicos.productapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cursomicrosservicos.productapi.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByNameIgnoreCaseContaining(String name);

    List<Supplier> findByName(String name);
    
}