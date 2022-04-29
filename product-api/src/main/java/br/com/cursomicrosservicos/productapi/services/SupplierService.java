package br.com.cursomicrosservicos.productapi.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.config.success.SuccessResponse;
import br.com.cursomicrosservicos.productapi.dto.supplier.SupplierRequest;
import br.com.cursomicrosservicos.productapi.dto.supplier.SupplierResponse;
import br.com.cursomicrosservicos.productapi.models.Supplier;
import br.com.cursomicrosservicos.productapi.repositories.ProductRepository;
import br.com.cursomicrosservicos.productapi.repositories.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    public SupplierResponse save(SupplierRequest supplierRequest) {
        validateSupplier(supplierRequest);
        Supplier supplierSaved = supplierRepository.save(Supplier.of(supplierRequest));
        return SupplierResponse.of(supplierSaved);
    }

    public Supplier findById(Integer id) {
        validateInformedId(id);
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

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if (productRepository.existsBySupplierId(id)) {
            throw new ExceptionValidation("You cannot delete this supplier because it's already defined by a product.");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted.");
    }

    public SupplierResponse update(SupplierRequest supplierRequest, Integer id) {
        validateInformedId(id);
        validateSupplier(supplierRequest);

        Supplier supplier = Supplier.of(supplierRequest);
        supplier.setId(id);

        supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    /* Validadores */

    private void validateSupplier(SupplierRequest supplierRequest) {
        if (supplierRequest == null || supplierRequest.getName() == null || supplierRequest.getName().isEmpty()) {
            throw new ExceptionValidation("The supplier name was not informed.");
        }
    }

    private void validateInformedId(Integer id) {
        if (id == null) {
            throw new ExceptionValidation("The id must be informed.");
        }
    }
    
}