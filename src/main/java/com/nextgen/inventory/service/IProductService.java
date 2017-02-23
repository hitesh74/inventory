package com.nextgen.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nextgen.inventory.dto.ProductDto;
import com.nextgen.inventory.entity.Product;

public interface IProductService {

	Page<Product> getProducts(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId);

	Product save(ProductDto productDto, Integer userId);

	List<Product> getItemsSummaryList(Integer userId);

}
