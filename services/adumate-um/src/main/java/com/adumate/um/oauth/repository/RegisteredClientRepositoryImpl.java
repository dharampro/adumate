package com.adumate.um.oauth.repository;

import com.adumate.um.oauth.domain.MongoDBRegisteredClient;
import com.adumate.um.oauth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private final MongoDBRegisteredClientRepository repository;

    @Override
    public void save(RegisteredClient registeredClient) {
        this.repository.save(from(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        return this.repository.findById(id)
                .map(this::to)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.repository.findByClientId(clientId)
                .map(this::to)
                .orElse(null);
    }

    public RegisteredClient to(MongoDBRegisteredClient client) {
        if (client == null) {
            return null;
        }

        final List<ClientAuthenticationMethod> methods = client.getClientAuthenticationMethods().stream()
                .map(this::resolveClientAuthenticationMethod)
                .toList();

        final List<AuthorizationGrantType> grantTypes = client.getAuthorizationGrantTypes().stream()
                .map(this::resolveAuthorizationGrantType)
                .toList();

        final ClientSettings clientSettings = ClientSettings.builder()
                .requireProofKey(client.getClientSettings().isRequireProofKey())
                .requireAuthorizationConsent(client.getClientSettings().isRequireAuthorizationConsent())
                .build();

        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.from(client.getTokenSettings().getIdTokenSignatureAlgorithm().getName());

        final TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenTimeToLive(client.getTokenSettings().getAccessTokenTimeToLive())
                .reuseRefreshTokens(client.getTokenSettings().isReuseRefreshTokens())
                .refreshTokenTimeToLive(client.getTokenSettings().getRefreshTokenTimeToLive())
                .idTokenSignatureAlgorithm(signatureAlgorithm)
                .build();

        return RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecret(client.getClientSecret())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(clientMethods ->
                        clientMethods.addAll(methods)
                )
                .authorizationGrantTypes(authorizationTypes ->
                        authorizationTypes.addAll(grantTypes)
                )
                .redirectUris(redirectUris -> redirectUris.addAll(client.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(client.getScopes()))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }

    public MongoDBRegisteredClient from(RegisteredClient registeredClient) {
        Instant clientIdIssuedAt = registeredClient.getClientIdIssuedAt() != null ?
                registeredClient.getClientIdIssuedAt() : Instant.now();

        Instant clientSecretExpiresAt = registeredClient.getClientSecretExpiresAt() != null ?
                registeredClient.getClientSecretExpiresAt() : null;

        final List<CustomOauthMethods> clientAuthenticationMethods = registeredClient.getClientAuthenticationMethods().stream()
                .map(this::resolveOauth2ClientAuthenticationMethod)
                .toList();

        final List<CustomGrantTypes> authorizationGrantTypes = registeredClient.getAuthorizationGrantTypes().stream()
                .map(this::resolveOauth2AuthorizationGrantType)
                .toList();

        final CustomClientSettings clientSettings = new CustomClientSettings(registeredClient.getClientSettings().isRequireProofKey(), registeredClient.getClientSettings().isRequireAuthorizationConsent());

        final CustomSignatureAlgorithm signatureAlgorithm = CustomSignatureAlgorithm.from(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm().getName());
        final CustomTokenSetting tokenSettings = new CustomTokenSetting(registeredClient.getTokenSettings().getAccessTokenTimeToLive(), registeredClient.getTokenSettings().isReuseRefreshTokens(), registeredClient.getTokenSettings().getRefreshTokenTimeToLive(), signatureAlgorithm);

        return new MongoDBRegisteredClient(
                registeredClient.getId(),
                registeredClient.getClientId(),
                clientIdIssuedAt,
                registeredClient.getClientSecret(),
                clientSecretExpiresAt,
                registeredClient.getClientName(),
                clientAuthenticationMethods,
                authorizationGrantTypes,
                registeredClient.getRedirectUris(),
                registeredClient.getScopes(),
                clientSettings,
                tokenSettings
        );
    }


    private ClientAuthenticationMethod resolveClientAuthenticationMethod(CustomOauthMethods clientAuthenticationMethod) {
        if (CustomOauthMethods.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod.getValue())) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (CustomOauthMethods.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod.getValue())) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (CustomOauthMethods.NONE.getValue().equals(clientAuthenticationMethod.getValue())) {
            return ClientAuthenticationMethod.NONE;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod.getValue());
    }

    private CustomOauthMethods resolveOauth2ClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        if (CustomOauthMethods.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod.getValue())) {
            return CustomOauthMethods.CLIENT_SECRET_BASIC;
        } else if (CustomOauthMethods.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod.getValue())) {
            return CustomOauthMethods.CLIENT_SECRET_POST;
        } else if (CustomOauthMethods.NONE.getValue().equals(clientAuthenticationMethod.getValue())) {
            return CustomOauthMethods.NONE;
        }
        return CustomOauthMethods.NONE;
    }

    private AuthorizationGrantType resolveAuthorizationGrantType(CustomGrantTypes authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType.getValue())) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType.getValue())) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType.getValue())) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType.getValue());
    }

    private CustomGrantTypes resolveOauth2AuthorizationGrantType(AuthorizationGrantType authorizationGrantType) {
        if (CustomGrantTypes.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType.getValue())) {
            return CustomGrantTypes.AUTHORIZATION_CODE;
        } else if (CustomGrantTypes.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType.getValue())) {
            return CustomGrantTypes.CLIENT_CREDENTIALS;
        } else if (CustomGrantTypes.REFRESH_TOKEN.getValue().equals(authorizationGrantType.getValue())) {
            return CustomGrantTypes.REFRESH_TOKEN;
        }
        return CustomGrantTypes.PASSWORD;
    }
}
