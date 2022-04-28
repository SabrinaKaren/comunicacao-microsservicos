package br.com.cursomicrosservicos.productapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cursomicrosservicos.productapi.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    
}