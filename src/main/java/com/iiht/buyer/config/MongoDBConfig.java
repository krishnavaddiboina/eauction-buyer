package com.iiht.buyer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


@Configuration
@EnableMongoRepositories
public class MongoDBConfig extends AbstractMongoClientConfiguration {
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;
	
	@Value("${spring.data.mongodb.uri}")
	private String connectionString;
 
    @Override
    public MongoClient mongoClient() {        
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .build();
        
        return MongoClients.create(mongoClientSettings);
    }

	@Override
	protected String getDatabaseName() {		
		return databaseName;
	}
} 