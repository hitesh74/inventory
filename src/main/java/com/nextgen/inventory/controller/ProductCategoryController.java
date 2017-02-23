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
import com.nextgen.inventory.dto.ProductCategoryDto;
import com.nextgen.inventory.entity.ProductCategory;
import com.nextgen.inventory.service.IProductCategoryService;

@RestController
@RequestMapping("/api/product/category")
public class ProductCategoryController extends AbstractController {

	@Autowired
	IProductCategoryService productCategoryService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addCategory(@RequestBody ProductCategoryDto productCategoryDto) {
		Integer userId = getLoggedInUser().getUserId();
		ProductCategory productCategory = productCategoryService.save(productCategoryDto, userId);
	}

	@RequestMapping(value = "/get/list/summary", method = RequestMethod.GET)
	public List<ProductCategoryDto> getCategoriesSummaryList() {

		Integer userId = getLoggedInUser().getUserId();
		List<ProductCategory> categories = productCategoryService.getCategoriesSummaryList(userId);

		List<ProductCategoryDto> categoryDtos = new ArrayList<>();

		for (ProductCategory category : categories) {
			ProductCategoryDto categoryDto = new ProductCategoryDto();

			categoryDto.setCategoryId(category.getCategoryId());
			categoryDto.setName(category.getName());

			categoryDtos.add(categoryDto);
		}

		return categoryDtos;
	}

	@RequestMapping(value = "/get/page/{pageNumber}/{pageSize}")
	public PageDto<ProductCategoryDto> getProductCategoriesPage(@PathVariable int pageNumber, @PathVariable int pageSize,
			@RequestParam(required = false) String sortType, @RequestParam(required = false) Boolean sortAsc, @RequestParam(required = false) String searchText) {

		Integer userId = getLoggedInUser().getUserId();
		Page<ProductCategory> poductCategoriesPage = productCategoryService.getProductCategories(pageNumber, pageSize, sortType, sortAsc, searchText, userId);

		List<ProductCategoryDto> productCategoryDtos = new ArrayList<>();
		for (ProductCategory productCategory : poductCategoriesPage.getContent()) {

			ProductCategoryDto productCategoryDto = new ProductCategoryDto();
			BeanUtils.copyProperties(productCategory, productCategoryDto);

			productCategoryDtos.add(productCategoryDto);
		}

		PageDto<ProductCategoryDto> productCategoriesPageDto = new PageDto<ProductCategoryDto>(productCategoryDtos);
		productCategoriesPageDto.setTotalPages(poductCategoriesPage.getTotalPages());
		productCategoriesPageDto.setTotalElements(poductCategoriesPage.getTotalElements());

		return productCategoriesPageDto;
	}

}
