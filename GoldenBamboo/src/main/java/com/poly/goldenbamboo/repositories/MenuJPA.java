package com.poly.goldenbamboo.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.goldenbamboo.entities.MenuEntity;

public interface MenuJPA extends JpaRepository<MenuEntity, Integer>{
	List<MenuEntity> findByBranchId(Integer branchId);

}