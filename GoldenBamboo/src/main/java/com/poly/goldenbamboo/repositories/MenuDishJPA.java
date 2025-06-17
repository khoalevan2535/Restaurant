package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.MenuDishEntity;

public interface MenuDishJPA extends JpaRepository<MenuDishEntity, Integer>{
//	@Query("SELECT md FROM MenuDishEntity md WHERE md.menus.branch.id = :branchId")
//	List<MenuDishEntity> findByBranchId(@Param("branchId") Integer branchId);

}	

