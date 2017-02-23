package com.nextgen.inventory.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nextgen.inventory.entity.Product;

public class ProductSpecs {

	public static Specification<Product> matchAllFields(String searchText) {
		return new Specification<Product>() {
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				String strValue = '%' + searchText + '%';

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(builder.like(root.get("name"), strValue));

				return builder.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}

	public static Specification<Product> matchUser(Integer userId) {
		return new Specification<Product>() {
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> userIdPath = root.join("user").get("userId");
				return builder.equal(userIdPath, userId);

			}
		};
	}
}
