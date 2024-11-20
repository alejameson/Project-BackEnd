package com.example.CrudSuppliers.services;

import com.example.CrudSuppliers.model.Supplier;
import com.example.CrudSuppliers.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SupplierService {

    private final SupplierRepository supplierRepository;

    /********** METODO PARA OBTENER Proveedor **********/
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    /********** METODO PARA CREAR Proveedor **********/
    public ResponseEntity<Object> newSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier created successfully", HttpStatus.CREATED);
    }

    /********** METODO PARA ELIMINAR Proveedor **********/
    public ResponseEntity<Object> deleteSupplier(Long id) {
        Optional<Supplier> existingSupplierOptional = supplierRepository.findById(id);
        if (existingSupplierOptional.isPresent()) {
            supplierRepository.deleteById(id);
            return new ResponseEntity<>("Supplier deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Supplier not found", HttpStatus.NOT_FOUND);
        }
    }

    /********** METODO PARA ACTUALIZAR PROVEEDOR **********/
    public ResponseEntity<Object> updateSupplier(Long id, Supplier updatedSupplier) {
        Optional<Supplier> existingSupplierOptional = supplierRepository.findById(id);
        if (existingSupplierOptional.isPresent()) {
            Supplier existingSupplier = existingSupplierOptional.get();
            existingSupplier.setSku(updatedSupplier.getSku());
            existingSupplier.setName(updatedSupplier.getName());
            existingSupplier.setStatus(updatedSupplier.getStatus());

            supplierRepository.save(existingSupplier);

            return new ResponseEntity<>("Supplier updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Supplier not found", HttpStatus.NOT_FOUND);
        }
    }

    /********** METODO PARA BUSCAR PROVEEDOR POR ID **********/
    public ResponseEntity<Object> findByIdSupplier(Long id) {
        Optional<Supplier> existingSupplierOptional = supplierRepository.findById(id);
        if (existingSupplierOptional.isPresent()) {
            Supplier existingSupplier = existingSupplierOptional.get();
            return new ResponseEntity<>(existingSupplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Supplier not found", HttpStatus.NOT_FOUND);
        }
    }
}


