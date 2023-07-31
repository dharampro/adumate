package com.adumate.um.oauth.dto;

public enum CustomSignatureAlgorithm {
    RS256("RS256"),
    RS384("RS384"),
    RS512("RS512"),
    ES256("ES256"),
    ES384("ES384"),
    ES512("ES512"),
    PS256("PS256"),
    PS384("PS384"),
    PS512("PS512");

    private final String name;

    CustomSignatureAlgorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static CustomSignatureAlgorithm from(String name) {
        CustomSignatureAlgorithm[] var1 = values();

        for (CustomSignatureAlgorithm value : var1) {
            if (value.getName().equals(name)) {
                return value;
            }
        }

        return null;
    }
}
