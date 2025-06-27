package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.ComboEntity;

public interface ComboJPA extends JpaRepository<ComboEntity, Integer> {

    @Query("SELECT mc.combo FROM MenuComboEntity mc " +
            "JOIN mc.menu m " +
            "WHERE m.branch.id = :branchId " +
            "AND m.isDefault = true")
     List<ComboEntity> findDefaultMenuCombosByBranch(@Param("branchId") Integer branchId);

}