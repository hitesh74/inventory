package com.nextgen.inventory.dto;

import java.util.Date;
import java.util.List;

import com.nextgen.inventory.enums.ProductStatus;

public class ProductDto {

	private Integer productId;

	private String name;

	private ProductStatus status;

	private Date created;

	private Date updated;

	private List<ItemDto> itemDtos;

	private ProductCategoryDto productCategoryDto;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<ItemDto> getItemDtos() {
		return itemDtos;
	}

	public void setItemDtos(List<ItemDto> itemDtos) {
		this.itemDtos = itemDtos;
	}

	public ProductCategoryDto getProductCategoryDto() {
		return productCategoryDto;
	}

	public void setProductCategoryDto(ProductCategoryDto productCategoryDto) {
		this.productCategoryDto = productCategoryDto;
	}

}
