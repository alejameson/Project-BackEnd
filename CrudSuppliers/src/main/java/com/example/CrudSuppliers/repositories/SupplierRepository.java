package com.example.CrudSuppliers.repositories;

import com.example.CrudSuppliers.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{}
