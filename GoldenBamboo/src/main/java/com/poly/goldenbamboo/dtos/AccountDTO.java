package com.poly.goldenbamboo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
	private int id;
	
    @NotBlank(message = "Số điện thoại là bắt buộc!")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Số điện thoại không hợp lệ!")
    private String phone;

    @NotBlank(message = "Mật khẩu là bắt buộc!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
    private String password;
}
