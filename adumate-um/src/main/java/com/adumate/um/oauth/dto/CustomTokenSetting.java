package com.adumate.um.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomTokenSetting {
    private Duration accessTokenTimeToLive;
    private boolean reuseRefreshTokens = true;
    private Duration refreshTokenTimeToLive;
    private CustomSignatureAlgorithm idTokenSignatureAlgorithm;
}
