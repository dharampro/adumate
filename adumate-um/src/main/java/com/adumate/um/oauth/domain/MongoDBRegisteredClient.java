package com.adumate.um.oauth.domain;

import com.adumate.um.oauth.dto.CustomClientSettings;
import com.adumate.um.oauth.dto.CustomGrantTypes;
import com.adumate.um.oauth.dto.CustomOauthMethods;
import com.adumate.um.oauth.dto.CustomTokenSetting;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
public class MongoDBRegisteredClient {
    @Id
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private List<CustomOauthMethods> clientAuthenticationMethods;
    private List<CustomGrantTypes> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private CustomClientSettings clientSettings;
    private CustomTokenSetting tokenSettings;

    @Version
    private Long version;

    public MongoDBRegisteredClient(String id,
                                   String clientId,
                                   Instant clientIdIssuedAt,
                                   String clientSecret,
                                   Instant clientSecretExpiresAt,
                                   String clientName,
                                   List<CustomOauthMethods> clientAuthenticationMethods,
                                   List<CustomGrantTypes> authorizationGrantTypes,
                                   Set<String> redirectUris,
                                   Set<String> scopes,
                                   CustomClientSettings clientSettings,
                                   CustomTokenSetting tokenSettings) {
        this.id = id;
        this.clientId = clientId;
        this.clientIdIssuedAt = clientIdIssuedAt;
        this.clientSecret = clientSecret;
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        this.clientName = clientName;
        this.clientAuthenticationMethods = clientAuthenticationMethods;
        this.authorizationGrantTypes = authorizationGrantTypes;
        this.redirectUris = redirectUris;
        this.scopes = scopes;
        this.clientSettings = clientSettings;
        this.tokenSettings = tokenSettings;
    }
}
