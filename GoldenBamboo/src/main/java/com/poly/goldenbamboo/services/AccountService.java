package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.mappers.BranchMapper;
import com.poly.goldenbamboo.repositories.AccountJPA;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountService {

    @Autowired
    private AccountJPA accountJPA;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private BranchMapper branchMapper;
    
    public List<AccountDTO> getAllAccount() {
        return accountJPA.findAll()
                         .stream()
                         .map(accountMapper::toDTO)
                         .collect(Collectors.toList());
    }


    
    public AccountEntity login(String phone, String password) {
        AccountEntity user = accountJPA.findByPhone(phone);
        if (user == null) {
            return null; 
        }

        if (!user.getPassword().equals(password)) {
            return null; 
        }

        return user;
    }
    
    public AccountEntity getAccountById(Integer userId) {
        return accountJPA.findById(userId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    
    public BranchDTO getBranchByUserId(Integer userId) {
        BranchEntity branchEntity = accountJPA.findBranchByUserId(userId);
        if (branchEntity == null) {
            throw new EntityNotFoundException("Không tìm thấy chi nhánh cho userId = " + userId);
        }
        return branchMapper.toDTO(branchEntity);
    }
    
   
}