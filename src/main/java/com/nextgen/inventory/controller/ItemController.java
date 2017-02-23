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

import com.nextgen.inventort.exception.RecordNotFoundException;
import com.nextgen.inventory.dto.ItemDto;
import com.nextgen.inventory.dto.PageDto;
import com.nextgen.inventory.dto.ProductCategoryDto;
import com.nextgen.inventory.dto.ProductDto;
import com.nextgen.inventory.dto.SupplierDto;
import com.nextgen.inventory.entity.Item;
import com.nextgen.inventory.service.IItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController extends AbstractController {

	@Autowired
	IItemService itemService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addItem(@RequestBody ItemDto itemDto) {
		Integer userId = getLoggedInUser().getUserId();
		Item item = itemService.save(itemDto, userId);
	}

	@RequestMapping(value = "/get/page/{pageNumber}/{pageSize}", method = RequestMethod.GET)
	public PageDto<ItemDto> getProductsPage(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam(required = false) String sortType,
			@RequestParam(required = false) Boolean sortAsc, @RequestParam(required = false) String searchText,
			@RequestParam(required = false) Integer productId) {

		Integer userId = getLoggedInUser().getUserId();

		Page<Item> itemsPage = itemService.getItems(pageNumber, pageSize, sortType, sortAsc, searchText, productId, userId);

		List<ItemDto> itemDtos = new ArrayList<>();
		for (Item item : itemsPage.getContent()) {
			ItemDto itemDto = new ItemDto();
			BeanUtils.copyProperties(item, itemDto);

			SupplierDto suppDto = new SupplierDto();
			BeanUtils.copyProperties(item.getSupplier(), suppDto);
			itemDto.setSupplierDto(suppDto);

			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(item.getProduct(), productDto);
			itemDto.setProductDto(productDto);

			itemDtos.add(itemDto);
		}

		PageDto<ItemDto> itemsPageDto = new PageDto<ItemDto>(itemDtos);
		itemsPageDto.setTotalPages(itemsPage.getTotalPages());
		itemsPageDto.setTotalElements(itemsPage.getTotalElements());

		return itemsPageDto;
	}

	@RequestMapping(value = "/get/{barCode}", method = RequestMethod.GET)
	public ItemDto getItemByBarCode(@PathVariable String barCode) throws RecordNotFoundException {

		Integer userId = getLoggedInUser().getUserId();
		Item item = itemService.getItem(barCode, userId);

		if (item == null) {
			throw new RecordNotFoundException();
		}

		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(item, itemDto);

		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(item.getProduct(), productDto);
		itemDto.setProductDto(productDto);

		ProductCategoryDto productCategoryDto = new ProductCategoryDto();
		BeanUtils.copyProperties(item.getProduct().getProductCategory(), productCategoryDto);
		productDto.setProductCategoryDto(productCategoryDto);

		return itemDto;
	}
}
