package com.nextgen.inventory.service;

import org.springframework.data.domain.Page;

import com.nextgen.inventory.dto.ItemDto;
import com.nextgen.inventory.entity.Item;

public interface IItemService {

	Page<Item> getItems(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer productId, Integer userId);

	Item getItem(String barCode, Integer userId);

	Item save(ItemDto itemDto, Integer userId);

}
