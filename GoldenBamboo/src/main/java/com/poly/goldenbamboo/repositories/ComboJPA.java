package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.ComboEntity;

public interface ComboJPA extends JpaRepository<ComboEntity, Integer> {
    List<ComboEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descKeyword);
}