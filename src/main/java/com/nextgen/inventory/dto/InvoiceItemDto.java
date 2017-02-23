package com.nextgen.inventory.dto;


public class InvoiceItemDto {

	private Long invoiceItemId;

	private ItemDto itemDto;

	private Integer quantity;

	public Long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(Long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public ItemDto getItemDto() {
		return itemDto;
	}

	public void setItemDto(ItemDto itemDto) {
		this.itemDto = itemDto;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}