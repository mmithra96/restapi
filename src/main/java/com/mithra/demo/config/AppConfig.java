package com.mithra.demo.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AppConfig {
	/*
	 * Inserts a DynamoDbClient bean on IOC, for DB connections, by default the
	 * table is created in US-East-1 Region and AWS secret access keys and Ids are
	 * hardcoded here. these attribute can be made configurable using
	 * applicatiom.properties. and
	 */
	@Bean
	public DynamoDbClient getDynamoDbClient() {

		return DynamoDbClient.builder().endpointOverride(URI.create("http://dynamodb.us-east-1.amazonaws.com"))
				.region(Region.US_EAST_1)
				.credentialsProvider(StaticCredentialsProvider.create(
						AwsBasicCredentials.create("{access-secret-id}", "{access-secret-key}")))
				.build();

	}

	/*
	 * DynamoDbEnhancedClient - The DynamoDB Enhanced client is able to perform
	 * operations asynchronously by leveraging the underlying asynchronous APIs
	 * provided by the AWS SDK for Java 2.0
	 */
	@Bean
	public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
		return DynamoDbEnhancedClient.builder().dynamoDbClient(getDynamoDbClient()).build();
	}

}
