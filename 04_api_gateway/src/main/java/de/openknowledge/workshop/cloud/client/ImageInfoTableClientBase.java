package de.openknowledge.workshop.cloud.client;

import de.openknowledge.workshop.cloud.serverless.infrastructure.AwsClientProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public abstract class ImageInfoTableClientBase {

    // image info table name
    final static String IMAGE_INFO_TABLE_NAME = "imageinfo";

    // TODO fill in YOUR personal ACCESS KEY
    static final String ACCESS_KEY_ID = "YOUR_ACCESS_KEY_HERE";

    // TODO fill in YOUR personal SECRET KEY
    static final String SECRET_KEY = "YOUR_SECRET_KEY_HERE";

    // DynamoDB for given ACCESS_KEY_ID / SECRET_KEY combination
    static DynamoDbClient DYNAMO_DB_CLIENT =
            AwsClientProvider.getDynamoDbClientFor(ACCESS_KEY_ID, SECRET_KEY);

    // for use with local aws cli and default provider only
    // static final DynamoDbClient DYNAMO_DB_CLIENT =
    //        AwsClientProvider.getDynamoDbClient();


}
