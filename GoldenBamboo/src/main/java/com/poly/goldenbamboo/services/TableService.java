package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.mappers.TableMapper;
import com.poly.goldenbamboo.repositories.TableJPA;

@Service
public class TableService {
	@Autowired
	private TableJPA tableJPA;

	public List<TableEntity> getAllTable() {
		return tableJPA.findAll();
	}

	public List<TableDTO> getAllTableByBranchId(Integer branchId) {
		return tableJPA.findByBranchId(branchId).stream().map(TableMapper::toDTO).collect(Collectors.toList());
	}

	public TableEntity getTableById(Integer tableId) {
		return tableJPA.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
	}
	
	
}
