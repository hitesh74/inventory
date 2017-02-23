package com.nextgen.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nextgen.inventory.dto.SupplierDto;
import com.nextgen.inventory.entity.Supplier;

public interface ISupplierService {

	Page<Supplier> getSuppliers(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer itemId, Integer userId);

	Supplier getSupplier(Integer supplierId);

	Supplier save(SupplierDto supplierDto, Integer userId);

	List<Supplier> getSuppliersSummaryList(Integer userId);

}
