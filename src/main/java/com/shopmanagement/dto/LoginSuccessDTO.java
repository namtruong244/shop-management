package com.shopmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessDTO {
    private String accessToken;
    private String refreshToken;
}
