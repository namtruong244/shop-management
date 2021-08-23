package com.shopmanagement.component;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static com.shopmanagement.common.SecurityConstants.*;

@Component
public class JWT {
    private final Algorithm algorithm = Algorithm.HMAC512(SECRET);

    public String generateJWTToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        return TOKEN_PREFIX + com.auth0.jwt.JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + JWT_EXPIRATION_TIME))
                .withClaim(CmnConst.ROLE_FIELD, userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()).get(0))
                .sign(algorithm);
    }

    public String generateRefreshJWTToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        return TOKEN_PREFIX + com.auth0.jwt.JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + REFRESH_JWT_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String getUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public String getRole(DecodedJWT decodedJWT){
        return decodedJWT.getClaim(CmnConst.ROLE_FIELD).asString();
    }

    public DecodedJWT verifierJWT(String token) {
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
