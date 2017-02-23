package com.nextgen.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nextgen.inventory.dto.InvoiceDto;
import com.nextgen.inventory.dto.InvoiceItemDto;
import com.nextgen.inventory.entity.Invoice;
import com.nextgen.inventory.entity.InvoiceItem;
import com.nextgen.inventory.entity.Item;
import com.nextgen.inventory.entity.User;
import com.nextgen.inventory.repository.IInvoiceItemRepository;
import com.nextgen.inventory.repository.IInvoiceRepository;
import com.nextgen.inventory.repository.IItemRepository;
import com.nextgen.inventory.repository.IUserRepository;
import com.nextgen.inventory.repository.spec.InvoiceItemSpec;
import com.nextgen.inventory.repository.spec.InvoiceSpec;
import com.nextgen.inventory.service.IInvoiceService;
import com.nextgen.inventory.service.util.DateUtil;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	IInvoiceRepository invoiceRepo;

	@Autowired
	IInvoiceItemRepository invoiceItemRepo;

	@Autowired
	IUserRepository userRepo;

	@Autowired
	IItemRepository itemRepo;

	@Override
	@Transactional
	public Invoice save(InvoiceDto invoiceDto, Integer userId) {

		Invoice invoice = new Invoice();
		invoice.setInvoiceDate(DateUtil.getCurrentDate());
		invoice.setCustomerName(invoiceDto.getCustomerName());
		invoice.setCustomerAddress(invoiceDto.getCustomerAddress());
		invoice.setCustomerCityCountry(invoiceDto.getCustomerCityCountry());
		invoice.setCustomerMobile(invoiceDto.getCustomerMobile());
		invoice.setTotalAmount(invoiceDto.getTotalAmount());

		User user = new User();
		user.setUserId(userId);
		invoice.setUser(user);

		List<InvoiceItem> invoiceItems = new ArrayList<>();

		for (InvoiceItemDto invoiceItemDto : invoiceDto.getInvoiceItemDtos()) {

			// Create InvoiceItem object
			InvoiceItem invoiceItem = new InvoiceItem();

			invoiceItem.setQuantity(invoiceItemDto.getQuantity());

			Item item = new Item();
			item.setItemId(invoiceItemDto.getItemDto().getItemId());
			invoiceItem.setItem(item);

			invoiceItem.setInvoice(invoice);

			// Add InvoiceItem object
			invoiceItems.add(invoiceItem);

			itemRepo.updateStock(invoiceItemDto.getQuantity(), invoiceItemDto.getItemDto().getItemId());

		}

		invoice.setInvoiceItems(invoiceItems);

		invoice = invoiceRepo.save(invoice);
		return invoice;
	}

	@Override
	public Page<Invoice> getInvoices(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId) {
		PageRequest pageRequest;

		if (sortType == null) {
			pageRequest = new PageRequest(pageNumber - 1, pageSize);
		} else {
			pageRequest = new PageRequest(pageNumber - 1, pageSize, sortAsc ? Direction.ASC : Direction.DESC, sortType);
		}

		Specifications<Invoice> where = Specifications.where(InvoiceSpec.matchUser(userId));

		if (searchText != null) {
			where = where.and(InvoiceSpec.matchAllFields(searchText));
		}

		return invoiceRepo.findAll(where, pageRequest);
	}

	@Override
	public List<InvoiceItem> getInvoiceItems(Long invoiceId) {

		Specifications<InvoiceItem> where = Specifications.where(InvoiceItemSpec.matchInvoice(invoiceId));
		return invoiceItemRepo.findAll(where);

	}
}
