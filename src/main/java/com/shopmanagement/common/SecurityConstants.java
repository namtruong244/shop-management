package com.shopmanagement.common;

public class SecurityConstants {
    // Secret key of json web token
    public static final String SECRET = "MySecretKey";

    // Expiration time of json web token (2 days)
    public static final long JWT_EXPIRATION_TIME = 86_400_000;

    // Expiration time of refresh json web token (90 days)
    public static final long REFRESH_JWT_EXPIRATION_TIME = 7_776_000_000L;
    // Type of token
    public static final String TOKEN_PREFIX = "Bearer ";

}
