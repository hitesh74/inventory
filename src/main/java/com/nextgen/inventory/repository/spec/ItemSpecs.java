package com.nextgen.inventory.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nextgen.inventory.entity.Item;
import com.nextgen.inventory.service.util.Helper;

public class ItemSpecs {

	public static Specification<Item> matchAllFields(String searchText) {
		return new Specification<Item>() {
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				String strValue = '%' + searchText + '%';
				Integer intValue = Helper.parseInt(searchText);
				Float floatValue = Helper.parseFloat(searchText);

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(builder.like(root.get("barCode"), strValue));

				if (floatValue != null) {
					predicates.add(builder.equal(root.get("unitSP"), floatValue));
					predicates.add(builder.equal(root.get("unitMRP"), floatValue));
					predicates.add(builder.equal(root.get("taxPercent"), floatValue));
				}

				if (intValue != null) {
					predicates.add(builder.equal(root.get("unitsPurchased"), intValue));
					predicates.add(builder.equal(root.get("unitsSold"), intValue));
					predicates.add(builder.equal(root.get("unitsInStock"), intValue));
				}

				// TODO think about to include expiry date

				return builder.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}

	public static Specification<Item> matchProduct(Integer productId) {
		return new Specification<Item>() {
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> productIdPath = root.join("product").get("productId");
				return builder.equal(productIdPath, productId);

				// TODO enhance by creating a wrapper
				// http://stackoverflow.com/questions/21791793/query-from-combined-spring-data-specification-has-multiple-joins-on-same-table
			}
		};
	}

	public static Specification<Item> matchUser(Integer userId) {
		return new Specification<Item>() {
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Path<Integer> userIdPath = root.join("product").join("user").get("userId");
				return builder.equal(userIdPath, userId);

			}
		};
	}

	public static Specification<Item> matchBarCode(String barCode) {
		return new Specification<Item>() {
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get("barCode"), barCode);
			}
		};
	}
}