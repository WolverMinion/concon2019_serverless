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

import de.openknowledge.workshop.cloud.serverless.model.ImageInfo;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.paginators.ScanIterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dynamo DB provider encapsulating access to dynamo db image info table
 */
public class DynamoDBProvider  {

    // image info table name
    private static final String IMAGE_TABLE = "imageinfo";

    // dynamo db client
    private static final DynamoDbClient DYNAMO_DB_CLIENT = AwsClientProvider.getDynamoDbClient();

    /**
     * Reads image info with given id from dynamo db table
     *
     * @param imageId id of image info to return
     * @return image info
     */
    public static ImageInfo readImageInfo(String imageId) {

        Map<String, AttributeValue> item =
                new HashMap<String,AttributeValue>();

        Map<String, AttributeValue> findBy =
                new HashMap<String,AttributeValue>();

        findBy.put("id", AttributeValue.builder()
                .s(imageId).build());

        GetItemRequest getItemRequest =
                GetItemRequest.builder()
                .key(findBy)
                .tableName(IMAGE_TABLE)
                .build();

        try {

            GetItemResponse getItemResponse = DYNAMO_DB_CLIENT.getItem(getItemRequest);
            item = getItemResponse.item();
            ImageInfo imageInfo = itemToImageInfo(item);
            return imageInfo;

        } catch (Exception e) {
            System.err.println("could not READ image with id " + imageId);
            return null;
        }
    }

    /**
     * Stores given image info object in dynamo db image info table.
     * @param imageInfo image info to store
     */
    public static void storeImageInfo(ImageInfo imageInfo) {

        Map<String, AttributeValue> item =
                new HashMap<String,AttributeValue>();

        item = imageInfoToItem(imageInfo);

        PutItemRequest putItemRequest =
                PutItemRequest.builder()
                .tableName(IMAGE_TABLE)
                .item(item)
                .build();
        try {
            PutItemResponse getItemResponse = DYNAMO_DB_CLIENT.putItem(putItemRequest);
        } catch (Exception e) {
            System.err.println("could not STORE image with id " + imageInfo.getImageId());
        }
    }


    /**
     * Deletes image info object given by its unique id in dynamo db image info table.
     * @param imageId id of image info to delete
     */
    public static boolean deleteImage(String imageId) {

        Map<String, AttributeValue> key =
                new HashMap<String,AttributeValue>();

        key.put("id", AttributeValue.builder()
                .s(imageId).build());


        DeleteItemRequest deleteItemRequest =
                DeleteItemRequest.builder()
                .tableName(IMAGE_TABLE)
                .key(key)
                .build();
        try {
            DeleteItemResponse deleteItemResponse = DYNAMO_DB_CLIENT.deleteItem(deleteItemRequest);
            return true;
        } catch (Exception e) {
            System.err.println("could not DELETE image with id " + imageId);
            return false;
        }
    }

    /**
     * Lists all image info items of dynamo db image info table.
     * @return list of all image info objects
     */
    public static List<ImageInfo> findAll() {

        List<ImageInfo> images = new ArrayList<>();

        ScanRequest request =
                ScanRequest
                        .builder()
                        .tableName(IMAGE_TABLE)
                        .build();
        ScanIterable response = DYNAMO_DB_CLIENT.scanPaginator(request);

        for (ScanResponse page : response) {
            for (Map<String, AttributeValue> item : page.items()) {
                // Consume the items
                images.add(itemToImageInfo(item));
            }
        }
        return images;
    }

    /**
     * Lists all image info items of given owner of dynamo db image info table.
     *
     * @param owner owner to list image info items of
     * @return list of all image info objects of given owner
     */
    public static List<ImageInfo> findAllOf(String owner) {

        List<ImageInfo> images = new ArrayList<>();

        String filterExpression = "fileOwner = :val";

        Map<String, AttributeValue> expressionAttributeValues =
                new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(owner).build());

        ScanRequest request =
                ScanRequest
                        .builder()
                        .tableName(IMAGE_TABLE)
                        .filterExpression(filterExpression)
                        .expressionAttributeValues(expressionAttributeValues)
                        .build();
        ScanIterable response = DYNAMO_DB_CLIENT.scanPaginator(request);

        for (ScanResponse page : response) {
            for (Map<String, AttributeValue> item : page.items()) {
                // Consume the item
                images.add(itemToImageInfo(item));
            }
        }
        return images;
    }


    /*  ----------- PRIVATE METHODS  --------------  */


    /**
     * Converts dynamo db item to <code>ImageInfo</code> object.
     *
     * @param item dynamo db image item to convert
     * @return image info object
     */
    private static ImageInfo itemToImageInfo(Map<String, AttributeValue> item) {

        ImageInfo imageInfo = new ImageInfo(item.get("id").s());
        imageInfo.setDescription(item.get("description").s());
        imageInfo.setName(item.get("name").s());
        imageInfo.setOwner(item.get("fileOwner").s());
        imageInfo.setTitle(item.get("title").s());
        imageInfo.setTimestamp(new Long(item.get("createdAt").n()));
        return imageInfo;
    }

    /**
     * Converts <code>ImageInfo</code> object to dynamo db image info item.
     *
     * @param image image info object to convert
     * @return dynamo db image info item
     */
    private static Map<String, AttributeValue> imageInfoToItem(ImageInfo image) {

        Map<String, AttributeValue> item =
                new HashMap<String,AttributeValue>();

        item.put("id", AttributeValue.builder().s(image.getImageId()).build());
        item.put("description", AttributeValue.builder().s(image.getDescription()).build());
        item.put("name", AttributeValue.builder().s(image.getName()).build());
        item.put("title", AttributeValue.builder().s(image.getTitle()).build());
        item.put("fileOwner", AttributeValue.builder().s(image.getOwner()).build());
        item.put("createdAt", AttributeValue.builder().n(Long.toString(image.getTimestamp())).build());
        return item;
    }
}