package com.poly.goldenbamboo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
	
	private int roleId;
	
	@NotBlank(message = "Tên không được để trống")
	private int branchId;

	@NotBlank(message = "Số điện thoại không được để trống")
	private String phone;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String password;
	private String name;

	
}
