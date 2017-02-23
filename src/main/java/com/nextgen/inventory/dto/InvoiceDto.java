package com.nextgen.inventory.dto;

import java.util.Date;
import java.util.List;

public class InvoiceDto {

	private Long invoiceId;

	private String customerName;

	private String customerAddress;

	private String customerCityCountry;

	private String customerMobile;

	private Date invoiceDate;

	private List<InvoiceItemDto> invoiceItemDtos;

	private Float totalAmount;

	private String invoiceNumber;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCityCountry() {
		return customerCityCountry;
	}

	public void setCustomerCityCountry(String customerCityCountry) {
		this.customerCityCountry = customerCityCountry;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<InvoiceItemDto> getInvoiceItemDtos() {
		return invoiceItemDtos;
	}

	public void setInvoiceItemDtos(List<InvoiceItemDto> invoiceItemDtos) {
		this.invoiceItemDtos = invoiceItemDtos;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
