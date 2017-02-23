package com.nextgen.inventory.dto;

import java.util.Date;

public class ItemDto {

	private Long itemId;

	private String barCode;

	private Float unitSP;

	private Float unitMRP;

	private Integer unitsPurchased;

	private Integer unitsSold;

	private Float taxPercent;

	private Date expiryDate;

	private Integer unitsInStock;

	private SupplierDto supplierDto;

	private ProductDto productDto;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Float getUnitSP() {
		return unitSP;
	}

	public void setUnitSP(Float unitSP) {
		this.unitSP = unitSP;
	}

	public Float getUnitMRP() {
		return unitMRP;
	}

	public void setUnitMRP(Float unitMRP) {
		this.unitMRP = unitMRP;
	}

	public Integer getUnitsPurchased() {
		return unitsPurchased;
	}

	public void setUnitsPurchased(Integer unitsPurchased) {
		this.unitsPurchased = unitsPurchased;
	}

	public Integer getUnitsSold() {
		return unitsSold;
	}

	public void setUnitsSold(Integer unitsSold) {
		this.unitsSold = unitsSold;
	}

	public Float getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Float taxPercent) {
		this.taxPercent = taxPercent;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public SupplierDto getSupplierDto() {
		return supplierDto;
	}

	public void setSupplierDto(SupplierDto supplierDto) {
		this.supplierDto = supplierDto;
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public Integer getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Integer unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

}
