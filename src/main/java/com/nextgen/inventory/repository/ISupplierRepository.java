package com.nextgen.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nextgen.inventory.entity.Supplier;

public interface ISupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

	Supplier getSupplierBySupplierId(Integer supplierId);
}
