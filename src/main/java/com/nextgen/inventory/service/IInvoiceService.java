package com.nextgen.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nextgen.inventory.dto.InvoiceDto;
import com.nextgen.inventory.entity.Invoice;
import com.nextgen.inventory.entity.InvoiceItem;

public interface IInvoiceService {

	Invoice save(InvoiceDto invoiceDto, Integer userId);

	Page<Invoice> getInvoices(int pageNumber, int pageSize, String sortType, Boolean sortAsc, String searchText, Integer userId);

	List<InvoiceItem> getInvoiceItems(Long invoiceId);

}
