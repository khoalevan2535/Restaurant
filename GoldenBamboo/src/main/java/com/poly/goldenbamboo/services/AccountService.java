package com.poly.goldenbamboo.services;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.RegisterDTO;
import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.dtos.LoginDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.entities.RoleEntity;
import com.poly.goldenbamboo.mappers.BranchMapper;
import com.poly.goldenbamboo.repositories.AccountJPA;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountJPA accountJPA;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private BranchMapper branchMapper;

//    public List<AccountDTO> getAllAccount() {
//        return accountJPA.findAll()
//                         .stream()
//                         .map(accountMapper::toDTO)
//                         .collect(Collectors.toList());
//    }

	public AccountEntity register(RegisterDTO registerDTO) {
		// Kiểm tra phone đã tồn tại
		if (accountJPA.findByPhone(registerDTO.getPhone()) != null) {
			throw new IllegalArgumentException("Số điện thoại đã được sử dụng");
		}

		// Tạo và lưu user
		AccountEntity user = new AccountEntity();
		user.setPhone(registerDTO.getPhone());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		user.setName(registerDTO.getName());

		BranchEntity branch = new BranchEntity();
		branch.setId(registerDTO.getBranchId());
		user.setBranch(branch);

		RoleEntity role = new RoleEntity();
		role.setId(2);
		user.setRole(role);

		return accountJPA.save(user);
	}
	
	// com.poly.goldenbamboo.services.AccountService.java

	public AccountEntity login(LoginDTO loginDTO) {
	    AccountEntity user = accountJPA.findByPhone(loginDTO.getPhone());
	    if (user == null) {
	        throw new IllegalArgumentException("Số điện thoại không tồn tại");
	    }

	    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
	        throw new IllegalArgumentException("Mật khẩu không đúng");
	    }

	    return user; // Trả về AccountEntity chứa userId để lưu vào cookie
	}


	public AccountEntity getAccountById(Integer userId) {
		return accountJPA.findById(userId).orElseThrow(() -> new RuntimeException("Account not found"));
	}

	public BranchDTO getBranchByUserId(Integer userId) {
		BranchEntity branchEntity = accountJPA.findBranchByUserId(userId);
		if (branchEntity == null) {
			throw new EntityNotFoundException("Không tìm thấy chi nhánh cho userId = " + userId);
		}
		return branchMapper.toDTO(branchEntity);
	}

}