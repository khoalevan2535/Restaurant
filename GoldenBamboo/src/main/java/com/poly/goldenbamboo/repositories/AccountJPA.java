package com.poly.goldenbamboo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;

import java.util.List;

public interface AccountJPA extends JpaRepository<AccountEntity, Integer> {

	AccountEntity findByPhone(String phone);
	
	@Query("SELECT a.branch FROM AccountEntity a WHERE a.id = :accountId")
	BranchEntity findBranchByUserId(@Param("accountId") Integer userId);
}
