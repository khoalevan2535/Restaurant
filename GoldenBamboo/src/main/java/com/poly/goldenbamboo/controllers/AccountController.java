package com.poly.goldenbamboo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.AccountDTO;
import com.poly.goldenbamboo.dtos.LoginDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.services.AccountService;
import com.poly.goldenbamboo.services.LoginService;

@RestController
@RequestMapping("/Account")
public class AccountController {

//	@Autowired
//	private AccountService accountService;

//	@GetMapping
//	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
//	    return ResponseEntity.ok(accountService.getAllAccount());
//	}
//	
//	@GetMapping("/id")
//	public String getAccountId(@PathVariable("id") Integer id) {
//		
//		
//		return "user id";
//	}


}