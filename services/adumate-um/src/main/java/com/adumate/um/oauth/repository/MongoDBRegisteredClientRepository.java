package com.adumate.um.oauth.repository;

import com.adumate.um.oauth.domain.MongoDBRegisteredClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoDBRegisteredClientRepository extends MongoRepository<MongoDBRegisteredClient, String> {
    Optional<MongoDBRegisteredClient> findByClientId(String clientId);

}
