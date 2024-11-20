package com.example.CrudSuppliers.controllers;

import com.example.CrudSuppliers.model.Supplier;
import com.example.CrudSuppliers.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/suppliers")
@RequiredArgsConstructor

public class SupplierController {

    private final SupplierService supplierService;

    /********** CONTROLADOR PARA OBTENER Proveedor **********/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Supplier> getSuppliers() {return supplierService.getSuppliers();}

    /********** CONTROLADOR PARA CREAR Proveedor **********/
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object>newSupplier (@Valid @RequestBody Supplier supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            //Manejo de errores
            List <String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return supplierService.newSupplier(supplier);
    }

    /********** CONTROLADOR PARA ACTUALIZAR Proveedor **********/
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateSupplier(@PathVariable("id") Long id, @RequestBody Supplier updatedSupplier) {
        return supplierService.updateSupplier(id, updatedSupplier);
    }

    /********** CONTROLADOR PARA ELIMINAR Proveedor **********/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteSupplier(@PathVariable("id") Long id) {
        return supplierService.deleteSupplier(id);
    }

    /********** CONTROLADOR PARA COMPROBAR EXISTENCIA DE Proveedor **********/
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByIdSupplier(@PathVariable("id") Long id) {
        return supplierService.findByIdSupplier(id);
    }

}

