package com.ppetrica.facepalm.backend.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ppetrica.facepalm.backend.persistence.ImageMapper;
import com.ppetrica.facepalm.backend.persistence.UserMapper;

import com.ppetrica.facepalm.backend.util.S3Helper;
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
    public AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.defaultClient();
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

    @Bean
    public ImageMapper getImageMapper() {
        return Mappers.getMapper(ImageMapper.class);
    }
}
