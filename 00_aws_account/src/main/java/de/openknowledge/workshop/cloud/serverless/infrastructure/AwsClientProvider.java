/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.workshop.cloud.serverless.infrastructure;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

/**
 * Provider for different AWS Service clients.
 *
 * This provider is a convenient class to build Aws Service clients
 */
public class AwsClientProvider {

    // region to use: e.g. Frankfurt == EU_CENTRAL_1, LONDON == EU_WEST_2
    // for "your" region" see https://docs.aws.amazon.com/de_de/AWSEC2/latest/UserGuide/using-regions-availability-zones.html

    // TODO: adjust region to "your" default region
    private static Region DEFAULT_REGION = Region.EU_CENTRAL_1;

    // Some AWS services don't have Region specific endpoints. When using those services,
    // you must set the Region as Region.AWS_GLOBAL or Region.AWS_CN_GLOBAL.
    private static Region GLOBAL_REGION = Region.AWS_GLOBAL;

    /**
     * Builds a AWS dynamoDb client using the "default" region and
     * "default" profile credential provider
     *
     * @return AWS dynamoDb client
     */
    public static DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(DEFAULT_REGION)
                .build();
    }

    /**
     * Builds a AWS dynamoDb client using the "default" region
     * and given access key and secret key
     *
     * @param accessKey access key to use to build aws credentials
     * @param secretKey secret key to use to build aws credentials
     * @return AWS dynamoDb client
     */
    public static DynamoDbClient getDynamoDbClientFor(String accessKey, String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        return DynamoDbClient
                .builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(credentialsProvider)
                .build();
    }


    /**
     * Builds a AWS S3 client using the "default" region and
     * "default" profile credential provider
     *
     * @return AWS S3 client
     */
    public static S3Client getS3Client() {
        return S3Client.builder()
                .region(DEFAULT_REGION)
                .build();
    }

    /**
     * Builds a AWS S3 client using the "default" region and
     * "default" profile credential provider
     *
     * @param accessKey access key to use to build aws credentials
     * @param secretKey secret key to use to build aws credentials
     * @return AWS S3 client
     */
    public static S3Client getS3ClientFor(String accessKey, String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        return S3Client.builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    /**
     * Builds a AWS Kinesis client using the "default" region an
     * "default" profile credential provider
     *
     * @return AWS Kinesis client
     */
    public static KinesisClient getKinesisClient() {

        return KinesisClient.builder()
                .region(DEFAULT_REGION)
                .build();
    }


    /**
     * Builds a AWS Kinesis client using the "default" region an
     * "default" profile credential provider
     *
     * @param accessKey access key to use to build aws credentials
     * @param secretKey secret key to use to build aws credentials
     * @return AWS Kinesis client
     */
    public static KinesisClient getKinesisClientFor(String accessKey, String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        return KinesisClient.builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(credentialsProvider)
                .build();
    }

   /**
     * Builds a AWS SNS client using the "default" region an
     * "default" profile credential provider
     *
     * @return AWS SNS client
     */
    public static SnsClient getSNSClient() {
        return SnsClient.builder()
                .region(DEFAULT_REGION)
                .build();
    }

    /**
     * Builds a AWS SNS client using the "default" region an
     * "default" profile credential provider
     *
     * @param accessKey access key to use to build aws credentials
     * @param secretKey secret key to use to build aws credentials
     * @return AWS SNS client
     */
    public static SnsClient getSNSClientFor(String accessKey, String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        return SnsClient.builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(credentialsProvider)
                .build();
    }


}