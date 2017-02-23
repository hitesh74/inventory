package com.nextgen.inventory.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nextgen.inventory.entity.Supplier;

public class SupplierSpecs {

	public static Specification<Supplier> matchAllFields(String searchText) {
		return new Specification<Supplier>() {
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				String strValue = '%' + searchText + '%';

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(builder.like(root.get("name"), strValue));
				predicates.add(builder.like(root.get("email"), strValue));
				predicates.add(builder.like(root.get("mobile"), strValue));

				return builder.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}

	public static Specification<Supplier> matchProduct(Integer productId) {
		return new Specification<Supplier>() {
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> itemIdPath = root.join("item").get("itemId");
				return builder.equal(itemIdPath, productId);

				// TODO enhance by creating a wrapper
				// http://stackoverflow.com/questions/21791793/query-from-combined-spring-data-specification-has-multiple-joins-on-same-table
			}
		};
	}

	public static Specification<Supplier> matchUser(Integer userId) {
		return new Specification<Supplier>() {
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> userIdPath = root.join("user").get("userId");
				return builder.equal(userIdPath, userId);

			}
		};
	}

}
