package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.DishEntity;

public interface DishJPA extends JpaRepository<DishEntity, Integer> {
//	List<DishEntity> findByBranchId(Integer branchId);

//	 @Query("SELECT md.dishe FROM Menu_DishEntity md " +
//	           "JOIN md.menus m " +
//	           "WHERE m.branch.id = :branchId AND m.isDefault = true")
//	    List<DishEntity> findDefaultMenuDishesByBranch(@Param("branchId") Integer branchId);

//	@Query("SELECT md.dish FROM MenuDishEntity md " + "JOIN md.menu m "
//			+ "WHERE m.branch.id = :branch AND m.isDefault = true " + "AND md.dish.category.id = :category")
//	List<DishEntity> findDefaultMenuDishesByBranchAndCategory(@Param("branchId") Integer branchId,
//			@Param("categoryId") Integer categoryId);

	@Query("SELECT md.dish FROM MenuDishEntity md JOIN md.menu m WHERE m.branch.id = :branch AND m.isDefault = true AND md.dish.category.id = :category")
	List<DishEntity> findDefaultMenuDishesByBranchAndCategory(@Param("branch") Integer branch,
			@Param("category") Integer category);
}
