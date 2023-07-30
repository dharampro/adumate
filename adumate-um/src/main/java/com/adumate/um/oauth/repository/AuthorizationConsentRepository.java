package com.adumate.um.oauth.repository;

import com.adumate.um.oauth.domain.MongoDBAuthorizationConsent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorizationConsentRepository extends MongoRepository<MongoDBAuthorizationConsent, String> {
    Optional<MongoDBAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}