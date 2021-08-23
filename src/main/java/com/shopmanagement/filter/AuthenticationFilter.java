package com.shopmanagement.filter;

import com.shopmanagement.component.JWT;
import com.shopmanagement.dto.LoginSuccessDTO;
import com.shopmanagement.dto.ResponseDTO;
import com.shopmanagement.entity.User;
import com.shopmanagement.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWT jwt;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credentials = GsonUtil.fromJson(request.getReader(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (Exception e) {
            log.error("Login fail with credentials");
            throw new BadCredentialsException(null);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String accessToken = jwt.generateJWTToken(authentication);
        String refreshToken = jwt.generateRefreshJWTToken(authentication);
        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO(accessToken, refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(GsonUtil.toJson(ResponseDTO.builder().data(loginSuccessDTO).build()));
    }
}
