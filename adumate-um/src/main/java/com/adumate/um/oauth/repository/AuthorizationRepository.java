package com.adumate.um.oauth.repository;

import com.adumate.um.oauth.domain.MongoDBAuthorization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorizationRepository extends MongoRepository<MongoDBAuthorization, String> {
    Optional<MongoDBAuthorization> findByState(String state);

    Optional<MongoDBAuthorization> findByAuthorizationCode(String authorizationCode);

    Optional<MongoDBAuthorization> findByAccessToken(String accessToken);

    Optional<MongoDBAuthorization> findByRefreshToken(String refreshToken);

    Optional<MongoDBAuthorization> findByStateOrAuthorizationCodeOrAccessTokenOrRefreshToken(String token);
}
