package com.adumate.um.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomClientSettings {
    private boolean requireProofKey = false;
    private boolean requireAuthorizationConsent = false;
}
