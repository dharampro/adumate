package com.adumate.um.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@TypeAlias("MongoDbOAuth2AuthorizationConsent")
@Document("authorizationConsent")
@CompoundIndexes({
        @CompoundIndex(name = "primary_key", unique = true, def = "{'registeredClientId': 1, 'principalName': 1}")
})
@NoArgsConstructor
@AllArgsConstructor
public class MongoDBAuthorizationConsent {
    @Id
    private String id;

    private String registeredClientId;

    private String principalName;

    private String authorities;
}
