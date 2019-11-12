package de.openknowledge.workshop.cloud.client;

import de.openknowledge.workshop.cloud.serverless.infrastructure.DynamoDBTableUtils;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class ImageInfoTableDeletionClient extends ImageInfoTableClientBase {

    /**
     * Deletes dynamoDB table IMAGE_INFO_TABLE_NAME
     */
    public static void main(String[] args) {

        try {
            deleteTable(IMAGE_INFO_TABLE_NAME);
        } catch (Exception e) {
            System.err.println("Failed to delete image info table:");
            System.err.println(e.getMessage());
        }
        System.out.println("Success.");
    }

    //------------------------ private methods ------------------------

    /**
     * Deletes table with given name
     *
     * @param tableName name of table to delete
     */
    private static void deleteTable(String tableName) {

        DeleteTableRequest request = DeleteTableRequest.builder()
                .tableName(tableName)
                .build();
        try {

            DynamoDBTableUtils.deleteTableIfExists(DYNAMO_DB_CLIENT, request);

            System.out.println("Issuing DeleteTable request for " + tableName);
            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");
            DeleteTableResponse deleteTableResponse = DYNAMO_DB_CLIENT.deleteTable(request);
            System.out.println(deleteTableResponse.toString());

        } catch (DynamoDbException e) {

            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());

        }
        System.out.println("DONE");
    }

}
