package com.nextgen.inventory.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nextgen.inventory.dto.SupplierDto;
import com.nextgen.inventory.entity.Supplier;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.repository.ISupplierRepository;
import com.nextgen.inventory.repository.spec.SupplierSpecs;
import com.nextgen.inventory.service.ISupplierService;

@Service
public class SupplierServiceImpl implements ISupplierService {

	@Autowired
	ISupplierRepository supplierRepo;

	@Override
	public Page<Supplier> getSuppliers(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer itemId, Integer userId) {
		PageRequest request;
		if (sortType == null) {
			request = new PageRequest(pageNumber - 1, pageSize);
		} else {
			request = new PageRequest(pageNumber - 1, pageSize, sortAsc ? Direction.ASC : Direction.DESC, sortType);
		}

		Specifications<Supplier> where = Specifications.where(SupplierSpecs.matchUser(userId));

		if (searchText != null) {
			where = where.and(SupplierSpecs.matchAllFields(searchText));
		}

		if (itemId != null) {
			where = where.and(SupplierSpecs.matchProduct(itemId));
		}

		return supplierRepo.findAll(where, request);
	}

	@Override
	public Supplier getSupplier(Integer supplierId) {
		return supplierRepo.getSupplierBySupplierId(supplierId);
	}

	@Override
	public Supplier save(SupplierDto supplierDto, Integer userId) {
		Supplier supplier = new Supplier();
		BeanUtils.copyProperties(supplierDto, supplier);

		User user = new User();
		user.setUserId(userId);
		supplier.setUser(user);

		return supplierRepo.save(supplier);
	}

	@Override
	public List<Supplier> getSuppliersSummaryList(Integer userId) {

		Specifications<Supplier> where = Specifications.where(SupplierSpecs.matchUser(userId));
		return supplierRepo.findAll(where);
	}

}
