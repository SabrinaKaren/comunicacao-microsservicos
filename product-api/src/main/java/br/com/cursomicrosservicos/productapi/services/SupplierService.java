package br.com.cursomicrosservicos.productapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.dto.supplier.SupplierRequest;
import br.com.cursomicrosservicos.productapi.dto.supplier.SupplierResponse;
import br.com.cursomicrosservicos.productapi.models.Supplier;
import br.com.cursomicrosservicos.productapi.repositories.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponse save(SupplierRequest supplierRequest) {
        validateSupplier(supplierRequest);
        Supplier supplierSaved = supplierRepository.save(Supplier.of(supplierRequest));
        return SupplierResponse.of(supplierSaved);
    }

    public Supplier findById(Integer id) {
        return supplierRepository
            .findById(id)
            .orElseThrow(() -> new ExceptionValidation("There's no supplier for the given ID."));
    }

    /* Validadores */

    private void validateSupplier(SupplierRequest supplierRequest) {
        if (supplierRequest == null || supplierRequest.getName() == null || supplierRequest.getName().isEmpty()) {
            throw new ExceptionValidation("The supplier name was not informed.");
        }
    }
    
}