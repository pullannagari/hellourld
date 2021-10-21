package com.cloudflare.hellourld.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages="com.cloudflare.hellourld.repository")
public class MongoClientConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment environment;

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.cloudflare.hellourld");
    }

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("mongodb.database");
    }

    @Override
    public MongoClient mongoClient() {

        ConnectionString connstr = new ConnectionString(environment.getProperty("mongodb.connection.string"));

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connstr)
                .build();

        return MongoClients.create(clientSettings);
    }
}
