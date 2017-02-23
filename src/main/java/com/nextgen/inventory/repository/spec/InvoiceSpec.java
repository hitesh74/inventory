package com.nextgen.inventory.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nextgen.inventory.entity.Invoice;
import com.nextgen.inventory.service.util.Helper;

public class InvoiceSpec {

	public static Specification<Invoice> matchAllFields(String searchText) {
		return new Specification<Invoice>() {
			public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				String strValue = '%' + searchText + '%';
				Float floatValue = Helper.parseFloat(searchText);

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(builder.like(root.get("customerName"), strValue));
				predicates.add(builder.like(root.get("customerAddress"), strValue));
				predicates.add(builder.like(root.get("customerCityCountry"), strValue));
				predicates.add(builder.like(root.get("customerMobile"), strValue));
				predicates.add(builder.like(root.get("customerAddress"), strValue));

				if (floatValue != null) {
					predicates.add(builder.equal(root.get("totalAmount"), floatValue));
				}

				return builder.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}

	public static Specification<Invoice> matchUser(Integer userId) {
		return new Specification<Invoice>() {
			public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> userIdPath = root.join("user").get("userId");
				return builder.equal(userIdPath, userId);

			}
		};
	}

}
