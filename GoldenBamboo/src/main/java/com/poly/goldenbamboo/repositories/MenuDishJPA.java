package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.Menu_DishEntity;

public interface MenuDishJPA extends JpaRepository<Menu_DishEntity, Integer>{
	@Query("SELECT md FROM Menu_DishEntity md WHERE md.menus.branch.id = :branchId")
	List<Menu_DishEntity> findByBranchId(@Param("branchId") Integer branchId);

}	

