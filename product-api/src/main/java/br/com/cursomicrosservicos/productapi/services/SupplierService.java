package br.com.cursomicrosservicos.productapi.services;

import java.util.List;
import java.util.stream.Collectors;
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
        if (id == null) {
            throw new ExceptionValidation("The id must be informed.");
        }

        return supplierRepository
            .findById(id)
            .orElseThrow(() -> new ExceptionValidation("There's no supplier for the given ID."));
    }

    public List<SupplierResponse> findAll() {
        return supplierRepository
            .findAll()
            .stream()
            .map(SupplierResponse::of)
            .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ExceptionValidation("The name must be informed.");
        }

        return supplierRepository
            .findByName(name)
            .stream()
            .map(SupplierResponse::of)
            .collect(Collectors.toList());
    }

    /* Validadores */

    private void validateSupplier(SupplierRequest supplierRequest) {
        if (supplierRequest == null || supplierRequest.getName() == null || supplierRequest.getName().isEmpty()) {
            throw new ExceptionValidation("The supplier name was not informed.");
        }
    }
    
}