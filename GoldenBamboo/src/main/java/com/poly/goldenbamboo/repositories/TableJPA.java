package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.TableEntity;

public interface TableJPA extends JpaRepository<TableEntity, Integer> {
	List<TableEntity> findByBranchId(Integer branchId);
}
