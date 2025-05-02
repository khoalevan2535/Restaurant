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
	 
	 @Query("SELECT md.dishe FROM Menu_DishEntity md " +
		       "JOIN md.menus m " +
		       "WHERE m.branch.id = :branchId AND m.isDefault = true " +
		       "AND md.dishe.category.id = :categoryId")
		List<DishEntity> findDefaultMenuDishesByBranchAndCategory(@Param("branchId") Integer branchId,
		                                                          @Param("categoryId") Integer categoryId);

}
