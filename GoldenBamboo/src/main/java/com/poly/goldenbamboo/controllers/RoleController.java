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
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.entities.RoleEntity;
import com.poly.goldenbamboo.services.RoleService;

@RestController
@RequestMapping("/Role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/FindAll")
	public List<RoleEntity> getRole() {
		return roleService.getAllRole();
	}

	@PostMapping("/Add")
	public ResponseEntity<RoleEntity> addRole(@RequestBody RoleEntity role) {
		RoleEntity newRole = roleService.addRole(role);
		return ResponseEntity.ok(newRole);
	}

	@DeleteMapping("/Delete/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable("id") Integer id) {
		boolean isDeleted = roleService.deleteRoleById(id);
		if (isDeleted) {
			return ResponseEntity.ok("Xóa vai trò thành công!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy vai trò!");
	}

	@PutMapping("/Update/{id}")
	public ResponseEntity<RoleEntity> updateRole(@PathVariable("id") Integer id, @RequestBody RoleEntity role) {
		RoleEntity updatedRole = roleService.updateRole(id, role);
		return ResponseEntity.ok(updatedRole);
	}

}
