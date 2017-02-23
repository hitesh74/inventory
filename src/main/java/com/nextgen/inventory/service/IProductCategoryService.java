package com.nextgen.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nextgen.inventory.dto.ProductCategoryDto;
import com.nextgen.inventory.entity.ProductCategory;

public interface IProductCategoryService {

	Page<ProductCategory> getProductCategories(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId);

	ProductCategory save(ProductCategoryDto productCategoryDto, Integer userId);

	List<ProductCategory> getCategoriesSummaryList(Integer userId);

}
