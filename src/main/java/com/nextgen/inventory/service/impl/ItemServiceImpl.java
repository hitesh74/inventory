package com.nextgen.inventory.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nextgen.inventory.dto.ItemDto;
import com.nextgen.inventory.entity.Item;
import com.nextgen.inventory.entity.Product;
import com.nextgen.inventory.entity.Supplier;
import com.nextgen.inventory.repository.IItemRepository;
import com.nextgen.inventory.repository.spec.ItemSpecs;
import com.nextgen.inventory.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	IItemRepository itemRepo;

	@Override
	public Page<Item> getItems(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer productId, Integer userId) {
		PageRequest request;
		if (sortType == null) {
			request = new PageRequest(pageNumber - 1, pageSize);
		} else {
			request = new PageRequest(pageNumber - 1, pageSize, sortAsc ? Direction.ASC : Direction.DESC, sortType);
		}

		Specifications<Item> where = Specifications.where(ItemSpecs.matchUser(userId));

		if (searchText != null) {
			where = where.and(ItemSpecs.matchAllFields(searchText));
		}

		if (productId != null) {
			where = where.and(ItemSpecs.matchProduct(productId));
		}

		return itemRepo.findAll(where, request);
	}

	@Override
	public Item getItem(String barCode, Integer userId) {
		Specifications<Item> spec = Specifications.where(ItemSpecs.matchUser(userId)).and(ItemSpecs.matchBarCode(barCode));
		return itemRepo.findOne(spec);
	}

	@Override
	public Item save(ItemDto itemDto, Integer userId) {
		Item item = new Item();
		BeanUtils.copyProperties(itemDto, item);

		Product product = new Product();
		product.setProductId(itemDto.getProductDto().getProductId());
		item.setProduct(product);

		Supplier supplier = new Supplier();
		supplier.setSupplierId(itemDto.getSupplierDto().getSupplierId());
		item.setSupplier(supplier);

		// Props for new Item
		item.setUnitsSold(0);
		item.setUnitsInStock(itemDto.getUnitsPurchased());

		return itemRepo.save(item);
	}
}
