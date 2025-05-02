//package com.poly.goldenbamboo.auth;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String userId = null;
//        String roleId = null;
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
//        String requestUri = request.getRequestURI();
//
//        if (userId != null && roleId != null) {
//            if ("1".equals(roleId) && requestUri.startsWith("/Admin")) {
//                return true;
//            } else if ("2".equals(roleId) && requestUri.startsWith("/User")) {
//                return true;
//            }
//        }
//
//        response.sendRedirect("/Login");
//        return false;
//    }
//}
package com;

