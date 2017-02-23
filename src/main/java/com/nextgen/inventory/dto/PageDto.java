package com.nextgen.inventory.dto;

import java.util.List;

public class PageDto<T> {

	List<T> content;

	private int totalPages;

	private long totalElements;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public PageDto() {
		super();
	}

	public PageDto(List<T> content) {
		super();
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
