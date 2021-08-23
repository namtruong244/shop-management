package com.shopmanagement.component;

import com.shopmanagement.dto.ResponseDTO;
import com.shopmanagement.util.GsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointJWT implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseDTO<?> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid Email or Password")
                .build();
        String jsonResponse = GsonUtil.toJson(responseDTO);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(jsonResponse);
    }
}
