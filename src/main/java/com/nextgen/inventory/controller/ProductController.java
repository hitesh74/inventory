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

import com.nextgen.inventory.dto.ItemDto;
import com.nextgen.inventory.dto.PageDto;
import com.nextgen.inventory.dto.ProductDto;
import com.nextgen.inventory.entity.Item;
import com.nextgen.inventory.entity.Product;
import com.nextgen.inventory.service.IProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractController {

	@Autowired
	IProductService productService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addProduct(@RequestBody ProductDto productDto) {
		Integer userId = getLoggedInUser().getUserId();
		Product product = productService.save(productDto, userId);
	}

	@RequestMapping(value = "/get/list/summary", method = RequestMethod.GET)
	public List<ProductDto> getProductsSummaryList() {

		Integer userId = getLoggedInUser().getUserId();
		List<Product> products = productService.getItemsSummaryList(userId);

		List<ProductDto> productsDtos = new ArrayList<>();

		for (Product product : products) {
			ProductDto productDto = new ProductDto();

			productDto.setProductId(product.getProductId());
			productDto.setName(product.getName());

			productsDtos.add(productDto);
		}

		return productsDtos;
	}

	@RequestMapping(value = "/get/page/{pageNumber}/{pageSize}")
	public PageDto<ProductDto> getProductsPage(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam(required = false) String sortType,
			@RequestParam(required = false) Boolean sortAsc, @RequestParam(required = false) String searchText) {

		Integer userId = getLoggedInUser().getUserId();
		Page<Product> poductsPage = productService.getProducts(pageNumber, pageSize, sortType, sortAsc, searchText, userId);

		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : poductsPage.getContent()) {

			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);

			List<ItemDto> itemDtos = new ArrayList<>();
			for (Item item : product.getItems()) {
				ItemDto itemDto = new ItemDto();
				BeanUtils.copyProperties(item, itemDto);
				itemDtos.add(itemDto);
			}

			productDto.setItemDtos(itemDtos);
			productDtos.add(productDto);
		}

		PageDto<ProductDto> productsPageDto = new PageDto<ProductDto>(productDtos);
		productsPageDto.setTotalPages(poductsPage.getTotalPages());
		productsPageDto.setTotalElements(poductsPage.getTotalElements());

		return productsPageDto;
	}
}
