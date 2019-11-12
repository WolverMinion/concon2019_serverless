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

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import de.openknowledge.workshop.cloud.serverless.infrastructure.AwsClientProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Producer for Kinesis demo data stream
 */
public class KinesisTestDataProducer {

    // TODO replace with your AWS access key id
    public final static String ACCESS_KEY_ID = "YOUR_ACCESS_KEY_ID";

    // TODO replace with your AWS secret key
    public final static String SECRET_KEY = "YOUR_SECRET_KEY";

    // KinesisClient for given ACCESS_KEY_ID / SECRET_KEY combination
    private final static KinesisClient KINESIS_CLIENT =
            AwsClientProvider.getKinesisClientFor(ACCESS_KEY_ID, SECRET_KEY);

    // for use with local aws cli and default provider only
    // private final static KinesisClient KINESIS_CLIENT =
    //      AwsClientProvider.getKinesisClient();

    private final static String STREAM_NAME = "sensor-data-stream";

    // number of sensor data items to create
    private final static int SENSOR_DATA_COUNT = 200;
    private final static double SENSOR_DATA_MEAN_VALUE = 100.0f;
    private final static double SENSOR_DATA_VARIANCE = 2.0f;


    private static Random random = new Random();

    /**
     * Generates a number of sensor data in a given range of values
     * calculated on a mean basis an an appropriate variance;
     */
    public static final void main(String[] args) {

        // create and fill list of PutRecordsRequestEntry
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();

        for (int i = 0; i < SENSOR_DATA_COUNT; i++) {

            String data = String.valueOf(getGaussian(SENSOR_DATA_MEAN_VALUE, SENSOR_DATA_VARIANCE));
            System.out.println("Put data " + i + "/value " + data);

            PutRecordsRequestEntry putRecordsRequestEntry = PutRecordsRequestEntry.builder()
                    .data(SdkBytes.fromByteArray(String.valueOf(getGaussian(SENSOR_DATA_MEAN_VALUE, SENSOR_DATA_VARIANCE)).getBytes()))
                    .partitionKey(String.format("partitionKey-%d", i))
                    .build();

            putRecordsRequestEntryList.add(putRecordsRequestEntry);


        }

        System.out.println("************************");
        System.out.println(putRecordsRequestEntryList);
        System.out.println("************************");


        // initialize PutRecordRequest with list of PutRecordsRequestEntry objects
        PutRecordsRequest putRecordsRequest = PutRecordsRequest.builder()
                .streamName(STREAM_NAME)
                .records(putRecordsRequestEntryList)
                .build();

        PutRecordsResponse putRecordsResponse = KINESIS_CLIENT.putRecords(putRecordsRequest);

        // iterate over all records of PutRecordsResponse and log success/failure status
        final List<PutRecordsRequestEntry> failedRecordsList = new ArrayList<>();
        final List<PutRecordsResultEntry> putRecordsResultEntryList = putRecordsResponse.records();

        for (int i = 0; i < putRecordsResultEntryList.size(); i++) {
            final PutRecordsRequestEntry putRecordRequestEntry = putRecordsRequestEntryList.get(i);
            final PutRecordsResultEntry putRecordsResultEntry = putRecordsResultEntryList.get(i);

            // check for error code
            if (putRecordsResultEntry.errorCode() != null) {
                // store for retry
                failedRecordsList.add(putRecordRequestEntry);
                System.out.println("ERROR (" + putRecordsResultEntry.errorCode() + ") " + putRecordsResultEntry.errorMessage());
            } else {
                System.out.println("SUCCESS (Shard:" + putRecordsResultEntry.shardId() + ") " + putRecordsResultEntry.sequenceNumber());
            }
        }
    }

    /**
     * Generates a random value based on given parameters
     *
     * @param aMean mean value to use for calculation
     * @param aVariance variance to use for calculation
     * @return random value
     */
    private static double getGaussian(double aMean, double aVariance) {
        return aMean + (random.nextGaussian() * aVariance);
    }
}
