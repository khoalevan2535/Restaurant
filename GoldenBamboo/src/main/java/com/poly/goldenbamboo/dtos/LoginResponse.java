package com.poly.goldenbamboo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String token;
    private String type = "Bearer";
    private String phone;
}
