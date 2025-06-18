package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.exception.ResourceNoFoundException;
import com.poly.goldenbamboo.mappers.BranchMapper;
import com.poly.goldenbamboo.mappers.TableMapper;
import com.poly.goldenbamboo.repositories.TableJPA;

@Service
public class TableService {
	@Autowired
	private TableJPA tableJPA;

	public TableDTO createTable(TableDTO tableBeans) {
        TableEntity tableEntity = TableMapper.mapToTableEntity(tableBeans);
        TableEntity savedTable = tableJPA.save(tableEntity);
        return TableMapper.mapToTableBeans(savedTable);
    }

    public TableDTO getTableById(int tableId) {
        TableEntity tableEntity = tableJPA.findById(tableId)
                .orElseThrow(() -> new ResourceNoFoundException("Table not found with id: " + tableId));
        return TableMapper.mapToTableBeans(tableEntity);
    }


    public TableDTO updateTable(int tableId, TableDTO tableBeans) {
        TableEntity tableEntity = tableJPA.findById(tableId)
                .orElseThrow(() -> new ResourceNoFoundException("Table not found with id: " + tableId));
        // Update the fields of the entity with the new values from tableBeans
        tableEntity.setNumber(tableBeans.getNumber());
        tableEntity.setBranch(BranchMapper.mapToBranchEntityById(tableBeans.getBranchId()));
        // Add more fields as needed

        TableEntity updatedTable = tableJPA.save(tableEntity);
        return TableMapper.mapToTableBeans(updatedTable);
    }


    public void deleteTable(int tableId) {
        TableEntity tableEntity = tableJPA.findById(tableId)
                .orElseThrow(() -> new ResourceNoFoundException("Table not found with id: " + tableId));
        tableJPA.delete(tableEntity);
    }

    public List<TableDTO> getAllTables() {
        List<TableEntity> tables = tableJPA.findAll();
        return tables.stream().map(TableMapper::mapToTableBeans).toList();
    }

    public List<TableDTO> getAllTableByBranchId(Integer branchId) {
		return tableJPA.findByBranchId(branchId).stream().map(TableMapper::mapToTableBeans).collect(Collectors.toList());
	}
}
