package com.nextgen.inventory.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nextgen.inventory.dto.ProductDto;
import com.nextgen.inventory.entity.Product;
import com.nextgen.inventory.entity.ProductCategory;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.enums.ProductStatus;
import com.nextgen.inventory.repository.IProductRepository;
import com.nextgen.inventory.repository.spec.ProductSpecs;
import com.nextgen.inventory.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepo;

	@Override
	public Page<Product> getProducts(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId) {

		PageRequest pageRequest;

		if (sortType == null) {
			pageRequest = new PageRequest(pageNumber - 1, pageSize);
		} else {
			pageRequest = new PageRequest(pageNumber - 1, pageSize, sortAsc ? Direction.ASC : Direction.DESC, sortType);
		}

		Specifications<Product> where = Specifications.where(ProductSpecs.matchUser(userId));

		if (searchText != null) {
			where = where.and(ProductSpecs.matchAllFields(searchText));
		}

		return productRepo.findAll(where, pageRequest);
	}

	@Override
	public Product save(ProductDto productDto, Integer userId) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);

		User user = new User();
		user.setUserId(userId);
		product.setUser(user);

		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryId(productDto.getProductCategoryDto().getCategoryId());
		product.setProductCategory(productCategory);

		// set default status
		product.setStatus(ProductStatus.ACTIVE);

		return productRepo.save(product);
	}

	@Override
	public List<Product> getItemsSummaryList(Integer userId) {
		Specifications<Product> where = Specifications.where(ProductSpecs.matchUser(userId));
		return productRepo.findAll(where);
	}

}
