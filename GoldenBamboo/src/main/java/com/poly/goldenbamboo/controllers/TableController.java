package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.services.TableService;

@RestController
@RequestMapping("/api/table")
public class TableController {

	@Autowired
	private TableService tableService;

	@PostMapping
    public ResponseEntity<?> createTable(@RequestBody TableDTO tableBeans) {
        TableDTO createdTable = tableService.createTable(tableBeans);
        return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TableDTO>> getAllTable() {
        List<TableDTO> table = tableService.getAllTables();
        return ResponseEntity.ok(table);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<TableDTO> getTableByBranchId(@PathVariable("Id") int branchId) {
        TableDTO table = tableService.getTableById(branchId);
        return ResponseEntity.ok(table);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable("id") int tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.ok("Table deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTablePatch(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer branchId) {

        // Retrieve the table by id; adjust this code if your service returns an
        // Optional.
        TableDTO table = tableService.getTableById(id);
        if (table == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            if (number != null) {
                table.setNumber(number);
            }
            if (branchId != null) {
                table.setBranchId(branchId); // Ensure your TableBeans has a field to hold the branch id.
            }

            // Save the updated table using your service method.
            TableDTO updatedTable = tableService.updateTable(id, table);
            return ResponseEntity.ok(updatedTable);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating table: " + e.getMessage());
        }
    }
}
