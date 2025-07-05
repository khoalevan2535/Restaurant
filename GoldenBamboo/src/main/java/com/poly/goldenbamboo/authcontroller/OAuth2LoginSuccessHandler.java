package com.poly.goldenbamboo.authcontroller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        // TODO: xử lý tạo user nếu chưa có ở đây

        // Tạo cookie
        Cookie cookie = new Cookie("userEmail", email);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        // Chuyển về frontend (tuỳ bạn muốn redirect tới đâu)
        response.sendRedirect("http://localhost:3000"); // <-- FE React
    }
}
