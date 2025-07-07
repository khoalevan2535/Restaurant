package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.mappers.TableMapper;
import com.poly.goldenbamboo.repositories.TableJPA;

import jakarta.transaction.Transactional;

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

	
	// trong file services/TableService.java

	public TableDTO getTableById(Integer tableId) {
	    TableEntity tableEntity = tableJPA.findById(tableId)
	            .orElseThrow(); // <-- Sửa ở đây
	    return TableMapper.toDTO(tableEntity);
	}
	
	@Transactional
	public TableDTO updateStatus(Integer tableId, int newStatus) { // <-- Sửa thành Integer
	    TableEntity tableToUpdate = tableJPA.findById(tableId)
	            .orElseThrow() ;
	    
	    tableToUpdate.setStatus(newStatus);
	    
	    TableEntity savedTable = tableJPA.save(tableToUpdate);
	    
	    return TableMapper.toDTO(savedTable);
	}
}
