package ru.otus.hw.cofiguration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.hw.repositories.secondary.AuthorMongoRepository;

import static java.util.Collections.singletonList;

/**
 * Конфигурация создана на основе стать:
 * <a href="https://www.baeldung.com/mongodb-multiple-databases-spring-data">...</a>
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = AuthorMongoRepository.class, mongoTemplateRef = "secondaryMongoTemplate")
public class SecondaryDataConfig {

    @Primary
    @Bean(name = "secondaryMongoProperties")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public MongoProperties dataSourceSecondaryProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "secondaryMongoClient")
    public MongoClient mongoClient(
            @Qualifier("secondaryMongoProperties") MongoProperties dataSourceSecondaryProperties) {

        MongoCredential credential = MongoCredential
                .createCredential(dataSourceSecondaryProperties.getUsername(),
                        dataSourceSecondaryProperties.getAuthenticationDatabase(),
                        dataSourceSecondaryProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(
                                dataSourceSecondaryProperties.getHost(),
                                dataSourceSecondaryProperties.getPort()))))
                .credential(credential)
                .build());
    }

    @Primary
    @Bean(name = "secondaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("secondaryMongoClient") MongoClient mongoClient,
            @Qualifier("secondaryMongoProperties") MongoProperties dataSourceSecondaryProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, dataSourceSecondaryProperties.getDatabase());
    }

    @Primary
    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate mongoTemplate(
            @Qualifier("secondaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
