package com.nextgen.inventory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "item")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NamedEntityGraph(name = "findItemsOnly", attributeNodes = { @NamedAttributeNode("itemId"), @NamedAttributeNode("barCode") })
@NamedQueries({ @NamedQuery(name = "Item.findByAllLikeIgnoreCase", query = "from Item i where i.barCode like :searchText"), })
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "bar_code", nullable = false, length = 255)
	private String barCode;

	@Column(name = "unit_sp", nullable = false)
	private Float unitSP;

	@Column(name = "unit_mrp", nullable = false)
	private Float unitMRP;

	@Column(name = "units_purchased", nullable = false)
	private Integer unitsPurchased;

	@Column(name = "units_sold", nullable = false)
	private Integer unitsSold;

	@Column(name = "units_in_stock", nullable = false)
	private Integer unitsInStock;

	@Column(name = "tax_percent", nullable = false)
	private Float taxPercent;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date", nullable = false)
	private Date expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updated;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	Product product;

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Integer unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
