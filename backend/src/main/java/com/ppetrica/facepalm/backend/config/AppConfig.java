package com.ppetrica.facepalm.backend.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class AppConfig {
    @Value("${dynamodb.table_name}")
    private String tableName;

    @Bean
    public AmazonDynamoDB getDDBClient() {
        return AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(@NonNull AmazonDynamoDB ddbClient) {
        DynamoDBMapperConfig config = new DynamoDBMapperConfig.Builder()
            .withTableNameOverride(
                TableNameOverride.withTableNameReplacement(tableName)
            )
            .build();
        
        return new DynamoDBMapper(ddbClient, config);
    }

    @Bean
    public UserMapper getUserMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
