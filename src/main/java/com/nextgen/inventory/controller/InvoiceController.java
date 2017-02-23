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

import com.nextgen.inventory.dto.InvoiceDto;
import com.nextgen.inventory.dto.InvoiceItemDto;
import com.nextgen.inventory.dto.ItemDto;
import com.nextgen.inventory.dto.PageDto;
import com.nextgen.inventory.dto.ProductCategoryDto;
import com.nextgen.inventory.dto.ProductDto;
import com.nextgen.inventory.entity.Invoice;
import com.nextgen.inventory.entity.InvoiceItem;
import com.nextgen.inventory.service.IInvoiceService;
import com.nextgen.inventory.service.util.Helper;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController extends AbstractController {

	@Autowired
	IInvoiceService invoiceService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public InvoiceDto save(@RequestBody InvoiceDto invoiceDto) {
		System.out.println(invoiceDto.getCustomerAddress());
		System.out.println(invoiceDto.getCustomerName());

		Integer userId = getLoggedInUser().getUserId();
		Invoice invoice = invoiceService.save(invoiceDto, userId);

		invoiceDto = new InvoiceDto();
		invoiceDto.setInvoiceNumber(Helper.getInvoiceNumber(userId, invoice.getInvoiceId()));

		return invoiceDto;
	}

	@RequestMapping(value = "/items/{invoiceId}", method = RequestMethod.GET)
	public List<InvoiceItemDto> getInvoiceItems(@PathVariable Long invoiceId) {

		List<InvoiceItem> invoiceItems = invoiceService.getInvoiceItems(invoiceId);
		List<InvoiceItemDto> invoiceItemDtos = new ArrayList<>();

		for (InvoiceItem invoiceItem : invoiceItems) {
			InvoiceItemDto invoiceItemDto = new InvoiceItemDto();
			BeanUtils.copyProperties(invoiceItem, invoiceItemDto);

			ItemDto itemDto = new ItemDto();
			BeanUtils.copyProperties(invoiceItem.getItem(), itemDto);
			invoiceItemDto.setItemDto(itemDto);

			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(invoiceItem.getItem().getProduct(), productDto);
			invoiceItemDto.getItemDto().setProductDto(productDto);

			ProductCategoryDto productCategoryDto = new ProductCategoryDto();
			BeanUtils.copyProperties(invoiceItem.getItem().getProduct().getProductCategory(), productCategoryDto);
			invoiceItemDto.getItemDto().getProductDto().setProductCategoryDto(productCategoryDto);

			invoiceItemDtos.add(invoiceItemDto);
		}

		return invoiceItemDtos;

	}

	@RequestMapping(value = "/get/page/{pageNumber}/{pageSize}", method = RequestMethod.GET)
	public PageDto<InvoiceDto> getInvoicesPage(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam(required = false) String sortType,
			@RequestParam(required = false) Boolean sortAsc, @RequestParam(required = false) String searchText) {

		Integer userId = getLoggedInUser().getUserId();
		Page<Invoice> invoicesPage = invoiceService.getInvoices(pageNumber, pageSize, sortType, sortAsc, searchText, userId);

		List<InvoiceDto> invoiceDtos = new ArrayList<>();
		for (Invoice invoice : invoicesPage.getContent()) {

			InvoiceDto invoiceDto = new InvoiceDto();
			BeanUtils.copyProperties(invoice, invoiceDto);
			invoiceDto.setInvoiceNumber(Helper.getInvoiceNumber(userId, invoice.getInvoiceId()));

			invoiceDtos.add(invoiceDto);
		}

		PageDto<InvoiceDto> invoicesPageDto = new PageDto<InvoiceDto>(invoiceDtos);
		invoicesPageDto.setTotalPages(invoicesPage.getTotalPages());
		invoicesPageDto.setTotalElements(invoicesPage.getTotalElements());

		return invoicesPageDto;
	}

}
