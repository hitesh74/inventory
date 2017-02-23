package com.nextgen.inventory.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nextgen.inventory.dto.ProductCategoryDto;
import com.nextgen.inventory.entity.ProductCategory;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.repository.IProductCategoryRepository;
import com.nextgen.inventory.repository.spec.ProductCategorySpecs;
import com.nextgen.inventory.service.IProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

	@Autowired
	IProductCategoryRepository productCategoryRepo;

	@Override
	public Page<ProductCategory> getProductCategories(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId) {

		PageRequest pageRequest;

		if (sortType == null) {
			pageRequest = new PageRequest(pageNumber - 1, pageSize);
		} else {
			pageRequest = new PageRequest(pageNumber - 1, pageSize, sortAsc ? Direction.ASC : Direction.DESC, sortType);
		}

		Specifications<ProductCategory> where = Specifications.where(ProductCategorySpecs.matchUser(userId));

		if (searchText != null) {
			where = where.and(ProductCategorySpecs.matchAllFields(searchText));
		}

		return productCategoryRepo.findAll(where, pageRequest);
	}

	@Override
	public ProductCategory save(ProductCategoryDto productCategoryDto, Integer userId) {
		ProductCategory productCategory = new ProductCategory();
		BeanUtils.copyProperties(productCategoryDto, productCategory);

		User user = new User();
		user.setUserId(userId);
		productCategory.setUser(user);

		return productCategoryRepo.save(productCategory);
	}

	@Override
	public List<ProductCategory> getCategoriesSummaryList(Integer userId) {
		Specifications<ProductCategory> where = Specifications.where(ProductCategorySpecs.matchUser(userId));
		return productCategoryRepo.findAll(where);
	}

}
