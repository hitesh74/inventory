package com.nextgen.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nextgen.inventory.entity.ProductCategory;

public interface IProductCategoryRepository
		extends JpaRepository<ProductCategory, Integer>, JpaSpecificationExecutor<ProductCategory> {

}
