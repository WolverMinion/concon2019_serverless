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
package de.openknowledge.workshop.cloud.serverless.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import de.openknowledge.workshop.cloud.serverless.business.SensorDataValidator;

import static de.openknowledge.workshop.cloud.serverless.util.LambdaEnvironment.getEnvVarAsDouble;
import static java.lang.String.format;

/**
 * Lambda function to detect abnormalities of streaming data.
 *
 * This lambda function is connected to a kinesis stream. Whenever
 * data is added to the stream the function reads and interprets this data.
 */
// de.openknowledge.workshop.cloud.serverless.lambda.AnomalyDetector::handleRequest
public class AnomalyDetector implements RequestHandler<KinesisEvent, String> {


    private static double DEFAULT_LOWER_LIMIT = 90.00;
    private static double DEFAULT_UPPER_LIMIT = 102.00;

    private static String ENV_LOWER_LIMIT = "lowerLimit";
    private static String ENV_UPPER_LIMIT = "upperLimit";

    /**
     * Reads kinesis stream, extracts given sensor data and delegates the
     * abnormality detection to a specialized class.
     *
     * @param kinesisEvent Kinesis event with stream data
     * @param context Related context of the lambda function.
     * @return success string
     */
    @Override
    public String handleRequest(KinesisEvent kinesisEvent, Context context) {


        double lowerLimit = getEnvVarAsDouble(ENV_LOWER_LIMIT, DEFAULT_LOWER_LIMIT);
        double upperLimit = getEnvVarAsDouble(ENV_UPPER_LIMIT, DEFAULT_UPPER_LIMIT);

        // access context related logger
        LambdaLogger logger = context.getLogger();

        int counter = 0;
        int recordCount = kinesisEvent.getRecords().size();

        logger.log(format("Checking block of %d records for anomalies.", recordCount));

        // HOWTO iterate through all records of the <code>kinesisEvent</code> trigger
        //      for every <code>KinesisEvent.KinesisEventRecord</code> object
        //      - extract value via corresponding method of this class
        //      - check abnormality with the help of SensorDataValidator
        //          - if value is out of given range (lowerLimit, upperLimit) then log record id and value

        // Step 1: extract kinesis event records (record by record)
        //   Step 1.1: check for "abnormality" and log failure (if any)

        // Step 2: return status
        return "Stream data processed successfully.";
    }

    /**
     * Extracts sensor data value from kinesis record
     *
     * @param record kinesis record with sensor data value
     * @return sensor data value
     */
    private double extractValue(KinesisEvent.KinesisEventRecord record) {
        String data = new String(record.getKinesis().getData().array());
        return Double.valueOf(data);
    }

}