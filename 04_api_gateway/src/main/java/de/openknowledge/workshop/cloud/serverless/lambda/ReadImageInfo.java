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
import de.openknowledge.workshop.cloud.serverless.infrastructure.DynamoDBProvider;
import de.openknowledge.workshop.cloud.serverless.model.ImageInfo;
import de.openknowledge.workshop.cloud.serverless.model.ReadImageInfoRequest;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * AWS lambda request handler to read image info object from dynamo db
 */
// de.openknowledge.workshop.cloud.serverless.lambda.ReadImageInfo::handleRequest
public class ReadImageInfo implements RequestHandler<ReadImageInfoRequest, ImageInfo> {

    /**
     * Reads image info from dynamo db image info table
     *
     * @param request image info id wrapped via <code>ReadImageInfoRequest</code>
     * @param context aws lambda context
     * @return image info with given id
     */
    public ImageInfo handleRequest(ReadImageInfoRequest request, Context context) {

        LambdaLogger logger = context.getLogger();
        String imageId = request.getImageId();
        notNull(imageId, "id must not be null");
        logger.log("Looking up image with id " + imageId);

        return DynamoDBProvider.readImageInfo(imageId);
    }

}
