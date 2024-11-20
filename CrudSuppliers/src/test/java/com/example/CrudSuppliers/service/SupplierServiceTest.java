package com.example.CrudSuppliers.service;


import com.example.CrudSuppliers.model.Supplier;
import com.example.CrudSuppliers.repositories.SupplierRepository;
import com.example.CrudSuppliers.services.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SupplierServiceTest {


    @Autowired
    private SupplierService supplierService;

    @MockBean
    private SupplierRepository supplierRepository;

    @BeforeEach
    void setUp (){
        MockitoAnnotations.openMocks(this);
    }


    /*****************TEST PARA OBTENER PROVEEDORES*****************/
    @Test
    public  void testGetSuppliers() {
        Supplier supplier = new Supplier(12L, "SKU001", "Description1", true);
        List<Supplier> suppliers = Collections.singletonList(supplier);
        when(supplierRepository.findAll()).thenReturn(suppliers);
        List<Supplier> result = supplierService.getSuppliers();
        assertEquals(1, result.size());
        assertEquals("SKU001", result.get(0).getSku());
    }


    /*****************TEST PARA ELIMINAR PROVEEDOR*****************/
    @Test
    public void testDeleteSupplier(){
        Long supplierId = 12L;
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        // Configurar el comportamiento del repositorio
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        // Llamar al método a probar
        ResponseEntity<Object> response = supplierService.deleteSupplier(supplierId);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Supplier deleted successfully", response.getBody());

        // Verificar si el método deleteById fue llamado con el ID correcto
        verify(supplierRepository, times(1)).deleteById(supplierId);
    }


    /*****************TEST PARA CREAR PROVEEDOR*****************/
    @Test
    public void testNewSupplier() {
        // Crear PROVEEDOR ejemplo
        Supplier supplier = new Supplier();
        supplier.setSku("ABC123");
        supplier.setName("Test Product");
        supplier.setStatus(true); // Cambiar a tipo Boolean

        // Llamar al método a probar
        ResponseEntity<Object> response = supplierService.newSupplier(supplier);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Supplier created successfully", response.getBody());

        // Verificar si el método save fue llamado con el PROVEEDOR correcto
        verify(supplierRepository, times(1)).save(supplier);
    }


    /*****************TEST PARA ACTUALIZAR PROVEEDOR*****************/
    @Test
    public void testUpdateProduct() {
        // Creación de PROVEEDOR ejemplo actual
        Long id = 1L;
        Supplier existingSupplier = new Supplier();
        existingSupplier.setId(id);
        existingSupplier.setSku("ABC123");
        existingSupplier.setName("Existing Product");
        existingSupplier.setStatus(true); // Cambiar a tipo Boolean

        // Creacion de PROVEEDOR ejemplo actualizado
        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(id);
        updatedSupplier.setSku("XYZ789");
        updatedSupplier.setName("Updated Product");
        updatedSupplier.setStatus(false); // Cambiar a tipo Boolean

        // Simular el comportamiento del repositorio
        when(supplierRepository.findById(id)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        // Llamar al método a probar
        ResponseEntity<Object> response = supplierService.updateSupplier(id, updatedSupplier);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());

        // Verificar si el método save fue llamado con el PROVEEDOR actualizado
        verify(supplierRepository, times(1)).save(existingSupplier);

        // Verificar los atributos del PROVEEDOR actualizado
        assertEquals("XYZ789", existingSupplier.getSku());
        assertEquals("Updated Product", existingSupplier.getName());
        assertEquals(false, existingSupplier.getStatus());
    }

}
