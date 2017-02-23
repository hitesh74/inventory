package com.nextgen.inventory.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nextgen.inventory.entity.InvoiceItem;

public class InvoiceItemSpec {

	public static Specification<InvoiceItem> matchInvoice(Long invoiceId) {
		return new Specification<InvoiceItem>() {
			public Predicate toPredicate(Root<InvoiceItem> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Long> invoiceIdPath = root.join("invoice").get("invoiceId");
				return builder.equal(invoiceIdPath, invoiceId);

			}
		};
	}

}
