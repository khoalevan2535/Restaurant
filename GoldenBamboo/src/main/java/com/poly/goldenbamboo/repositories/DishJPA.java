package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.DishEntity;

public interface DishJPA extends JpaRepository<DishEntity, Integer>{
//	 @Query("SELECT md.dishe FROM Menu_DishEntity md " +
//	           "JOIN md.menus m " +
//	           "WHERE m.branch.id = :branchId AND m.isDefault = true")
//	    List<DishEntity> findDefaultMenuDishesByBranch(@Param("branchId") Integer branchId);
	 
//	 @Query("SELECT md.dish FROM MenuDishEntity md " +
//		       "JOIN md.menus m " +
//		       "WHERE m.branch.id = :branch AND m.isDefault = true " +
//		       "AND md.dish.category.id = :category")
//		List<DishEntity> findDefaultMenuDishesByBranchAndCategory(@Param("branchId") Integer branchId,
//		                                                          @Param("categoryId") Integer categoryId);

@Query("SELECT d FROM DishEntity d WHERE d.name = ?1")
    DishEntity findByName(String name);

    // @Query("SELECT d FROM DishEntity d JOIN Discount_DishEntity dd ON d.id =
    // dd.dishe.id")
    // List<DishEntity> findDiscountedDishes();

    @Query("SELECT d FROM DishEntity d LEFT JOIN Discount_DishEntity dd ON d.id = dd.dishe.id WHERE dd IS NOT NULL")
    List<DishEntity> findDiscountedDishes();

}
