package com.adumate.um.oauth.dto;

public enum CustomGrantTypes {
    /**
     * Authorization code o auth 2 authorization grant type.
     */
    AUTHORIZATION_CODE("authorization_code"),
    /**
     * Refresh token o auth 2 authorization grant type.
     */
    REFRESH_TOKEN("refresh_token"),
    /**
     * Client credentials o auth 2 authorization grant type.
     */
    CLIENT_CREDENTIALS("client_credentials"),
    /**
     * Password o auth 2 authorization grant type.
     */
    PASSWORD("password"),
    /**
     * Jwt bearer o auth 2 authorization grant type.
     */
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer");

    private final String value;

    CustomGrantTypes(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
