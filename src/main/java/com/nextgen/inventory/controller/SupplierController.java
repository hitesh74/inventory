package com.nextgen.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nextgen.inventory.dto.PageDto;
import com.nextgen.inventory.dto.SupplierDto;
import com.nextgen.inventory.entity.Supplier;
import com.nextgen.inventory.service.ISupplierService;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController extends AbstractController {

	@Autowired
	ISupplierService supplierService;

	@RequestMapping(value = "/get/{supplierId}", method = RequestMethod.GET)
	public SupplierDto getSupplier(@PathVariable Integer supplierId) {

		Supplier supplier = supplierService.getSupplier(supplierId);

		SupplierDto supplierDto = new SupplierDto();
		BeanUtils.copyProperties(supplier, supplierDto);

		return supplierDto;
	}

	@RequestMapping(value = "/get/list/summary", method = RequestMethod.GET)
	public List<SupplierDto> getSuppliersSummaryList() {

		Integer userId = getLoggedInUser().getUserId();
		List<Supplier> suppliers = supplierService.getSuppliersSummaryList(userId);

		List<SupplierDto> supplierDtos = new ArrayList<>();

		for (Supplier supplier : suppliers) {
			SupplierDto supplierDto = new SupplierDto();

			supplierDto.setSupplierId(supplier.getSupplierId());
			supplierDto.setName(supplier.getName());

			supplierDtos.add(supplierDto);
		}

		return supplierDtos;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addSupplier(@RequestBody SupplierDto supplierDto) {
		Integer userId = getLoggedInUser().getUserId();
		Supplier supplier = supplierService.save(supplierDto, userId);
	}

	@RequestMapping(value = "/get/page/{pageNumber}/{pageSize}")
	public PageDto<SupplierDto> getProductsPage(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam(required = false) String sortType,
			@RequestParam(required = false) Boolean sortAsc, @RequestParam(required = false) String searchText, @RequestParam(required = false) Integer itemId) {

		Integer userId = getLoggedInUser().getUserId();
		Page<Supplier> suppliersPage = supplierService.getSuppliers(pageNumber, pageSize, sortType, sortAsc, searchText, itemId, userId);

		List<SupplierDto> supplierDtos = new ArrayList<>();
		for (Supplier supplier : suppliersPage.getContent()) {

			SupplierDto supplierDto = new SupplierDto();
			BeanUtils.copyProperties(supplier, supplierDto);

			supplierDtos.add(supplierDto);
		}

		PageDto<SupplierDto> supplierPageDto = new PageDto<SupplierDto>(supplierDtos);
		supplierPageDto.setTotalPages(suppliersPage.getTotalPages());
		supplierPageDto.setTotalElements(suppliersPage.getTotalElements());

		return supplierPageDto;
	}

}
