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
package de.openknowledge.workshop.cloud.client;

import de.openknowledge.workshop.cloud.serverless.infrastructure.DynamoDBTableUtils;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Clazz to create and initialize image info dynamo db table
 */
public class ImageInfoTableCreatorClient extends ImageInfoTableClientBase {

    /**
     * Creates and initializes dynamoDB table IMAGE_INFO_TABLE_NAME
     */
    public static void main(String[] args) {

        deleteImage("044fea92-dac7-4565-a80b-0ea222aee497");

        /**
        try {

            // Parameter1: table name
            // Parameter2: reads per second
            // Parameter3: writes per second
            // Parameter4/5: partition key and data type
            // Parameter6/7: sort key and data type (if applicable)
            createTable(IMAGE_INFO_TABLE_NAME, 10L, 5L, "id", "S");

            // fill created table with demo data
            loadSampleImageInfo(IMAGE_INFO_TABLE_NAME);

        }
        catch (Exception e) {
            System.err.println("Program failed:");
            System.err.println(e.getMessage());
        }
        System.out.println("Success.");
         */
    }

    //------------------------ private methods ------------------------

    /**
     * Creates table with given parameters
     *
     * @param tableName name of table
     * @param readCapacityUnits capacity units of read operations
     * @param writeCapacityUnits capacity units of write operations
     * @param partitionKeyName name of partition key
     * @param partitionKeyType type of partition key
     */
    private static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
                                    String partitionKeyName, String partitionKeyType) {

        createTable(tableName, readCapacityUnits, writeCapacityUnits, partitionKeyName, partitionKeyType, null, null);
    }


    /**
     * Creates table with given parameters
     *
     * @param tableName name of table
     * @param readCapacityUnits capacity units of read operations
     * @param writeCapacityUnits capacity units of write operations
     * @param partitionKeyName name of partition key
     * @param partitionKeyType type of partition key
     * @param sortKeyName name of sort key
     * @param sortKeyType type of sort key
     */
    private static void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
                                    String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {

        CreateTableRequest.Builder builder =  CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(partitionKeyName)
                        .attributeType(partitionKeyType)  // ScalarAttributeType.S
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(partitionKeyName)
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(readCapacityUnits)
                        .writeCapacityUnits(writeCapacityUnits)
                        .build())
                .tableName(tableName);
        if (sortKeyName != null) {
            builder.keySchema(KeySchemaElement.builder()
                    .attributeName(sortKeyName)
                    .keyType(sortKeyType)
                    .build());
        }

        CreateTableRequest request = builder.build();

        try {
            CreateTableResponse response = DYNAMO_DB_CLIENT.createTable(request);
            System.out.println(response.tableDescription().tableName());

            System.out.println("Waiting for " + tableName + " to be CREATED ...this may take a while...");
            DynamoDBTableUtils.waitUntilActive(DYNAMO_DB_CLIENT, tableName);
            System.out.println("DONE");

        } catch (DynamoDbException e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }


    /**
     * Fills table with initial values
     *
     * @param tableName name of table to fill
     */
    private static void loadSampleImageInfo(String tableName) {

        HashMap<String,AttributeValue> dog =
                new HashMap<String,AttributeValue>();

        HashMap<String,AttributeValue> cat =
                new HashMap<String,AttributeValue>();

        HashMap<String,AttributeValue> mouse =
                new HashMap<String,AttributeValue>();

        dog.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        dog.put("description", AttributeValue.builder().s("Image of a dog").build());
        dog.put("name", AttributeValue.builder().s("dog.jpg").build());
        dog.put("title", AttributeValue.builder().s("super dog").build());
        dog.put("fileOwner", AttributeValue.builder().s("mobileLarson").build());
        dog.put("createdAt", AttributeValue.builder().n(Long.toString(new Date().getTime())).build());

        cat.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        cat.put("description", AttributeValue.builder().s("Image of a cat").build());
        cat.put("name", AttributeValue.builder().s("cat.jpg").build());
        cat.put("title", AttributeValue.builder().s("super cat").build());
        cat.put("fileOwner", AttributeValue.builder().s("mobileLarson").build());
        cat.put("createdAt", AttributeValue.builder().n(Long.toString(new Date().getTime())).build());

        mouse.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        mouse.put("description", AttributeValue.builder().s("Image of a mouse").build());
        mouse.put("name", AttributeValue.builder().s("mouse.jpg").build());
        mouse.put("title", AttributeValue.builder().s("super mouse").build());
        mouse.put("fileOwner", AttributeValue.builder().s("someone").build());
        mouse.put("createdAt", AttributeValue.builder().n(Long.toString(new Date().getTime())).build());

        PutItemRequest putItemRequestForCat = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(cat)
                .build();
        PutItemRequest putItemRequestForDog = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(dog)
                .build();
        PutItemRequest putItemRequestForMouse = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(mouse)
                .build();

        try {
            DYNAMO_DB_CLIENT.putItem(putItemRequestForCat);
            DYNAMO_DB_CLIENT.putItem(putItemRequestForDog);
            DYNAMO_DB_CLIENT.putItem(putItemRequestForMouse);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println("Failed to create item in " + tableName);
            System.err.println(e.getMessage());
        }
    }


    public static void deleteImage(String imageId) {

        Map<String, AttributeValue> key =
                new HashMap<String,AttributeValue>();

        key.put("id", AttributeValue.builder()
                .s(imageId).build());


        DeleteItemRequest deleteItemRequest =
                DeleteItemRequest.builder()
                        .tableName(IMAGE_INFO_TABLE_NAME)
                        .key(key)
                        .build();
        try {
            DeleteItemResponse deleteItemResponse = DYNAMO_DB_CLIENT.deleteItem(deleteItemRequest);
        } catch (Exception e) {
            System.err.println("could not DELETE image with id " + imageId);
            e.printStackTrace();
        }
    }

}
