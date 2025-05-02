package com.poly.goldenbamboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.entities.RoleEntity;
import com.poly.goldenbamboo.repositories.RoleJPA;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {
	@Autowired
	private RoleJPA roleJPA;

	public List<RoleEntity> getAllRole() {
		return roleJPA.findAll();
	}

	public RoleEntity addRole(RoleEntity role) {
		return roleJPA.save(role);
	}

	public boolean deleteRoleById(Integer id) {
		if (roleJPA.existsById(id)) {
			roleJPA.deleteById(id);
			return true;
		}
		return false;
	}

	public RoleEntity updateRole(Integer id, RoleEntity updatedRole) {
		return roleJPA.findById(id).map(role -> {
			role.setName(updatedRole.getName());
			role.setStatus(updatedRole.isStatus());
			return roleJPA.save(role);
		}).orElseThrow(() -> new RuntimeException("Fail"));
	}

}
