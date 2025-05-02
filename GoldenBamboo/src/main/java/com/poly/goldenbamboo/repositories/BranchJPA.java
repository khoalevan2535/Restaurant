package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.BranchEntity;

public interface BranchJPA extends JpaRepository<BranchEntity, Integer>{
	 List<BranchEntity> findByStatusTrue();
}
