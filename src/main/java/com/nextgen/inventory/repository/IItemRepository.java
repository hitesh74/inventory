package com.nextgen.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nextgen.inventory.entity.Item;

public interface IItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {

	Page<Item> findByAllLikeIgnoreCase(@Param("searchText") String searchText, Pageable pageable);

	Page<Item> findByAllIgnoreCase(String searchText, Pageable pageable);

	@Modifying
	@Query("update Item set units_sold = units_sold + :units_sold, units_in_stock = units_in_stock - :units_sold where itemId = :itemId")
	Integer updateStock(@Param("units_sold") Integer units_sold, @Param("itemId") Long itemId);

}
