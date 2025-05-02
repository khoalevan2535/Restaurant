package com.poly.goldenbamboo.authcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.LoginDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.services.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private LoginService loginService;

//    @GetMapping("/CheckLogin")
//    public ResponseEntity<?> checkLogin(HttpServletRequest request) {
//        String userId = null;
//        String roleId = null;
//
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("userId".equals(cookie.getName())) {
//                    userId = cookie.getValue();
//                }
//                if ("userRole".equals(cookie.getName())) {
//                    roleId = cookie.getValue();
//                }
//            }
//        }
//
//        if (userId != null && roleId != null) {
//            return ResponseEntity.ok(Map.of(
//                "success", true,
//                "userId", userId,
//                "roleId", roleId
//            ));
//        }
//
//        return ResponseEntity.ok(Map.of("success", false));
//    }

    @PostMapping
    public ResponseEntity<?>login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        AccountEntity user = loginService.login(loginDTO.getPhone(), loginDTO.getPassword());
        
        System.out.println("phone:"+ loginDTO.getPhone());
        System.out.println("password:"+ loginDTO.getPassword());
        
        if (user == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Sai số điện thoại hoặc mật khẩu"));
        }

        // Tạo cookie lưu userId
        Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
        userIdCookie.setPath("/");
        userIdCookie.setHttpOnly(false);
        userIdCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        // userIdCookie.setSecure(true); // Bật nếu dùng HTTPS
        response.addCookie(userIdCookie);

        // Tạo cookie lưu roleId
        String roleId = user.getRole() != null ? String.valueOf(user.getRole().getId()) : "0"; // Giá trị mặc định nếu role null
        Cookie userRoleCookie = new Cookie("userRole", roleId);
        userRoleCookie.setPath("/");
        userRoleCookie.setHttpOnly(false);
        userRoleCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        // userRoleCookie.setSecure(true); // Bật nếu dùng HTTPS
        response.addCookie(userRoleCookie);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Đăng nhập thành công",
            "data", user
        ));
    }
}