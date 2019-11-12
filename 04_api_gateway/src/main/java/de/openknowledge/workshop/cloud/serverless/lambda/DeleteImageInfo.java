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
import de.openknowledge.workshop.cloud.serverless.model.DeleteImageInfoRequest;

/**
 * AWS lambda request handler to delete image info object from dynamo db
 */
// de.openknowledge.workshop.cloud.serverless.lambda.DeleteImageInfo::handleRequest
public class DeleteImageInfo implements RequestHandler<DeleteImageInfoRequest, String> {


    /**
     * Stores image info in dynamo db image info table
     *
     * @param request image info id wrapped via <code>DeleteImageInfoRequest</code>
     * @param context aws lambda context
     * @return status of deletion
     */
    public String handleRequest(DeleteImageInfoRequest request, Context context) {

        // access context related logger
        LambdaLogger logger = context.getLogger();

        String id = request.getImageId();

        logger.log("Delete image with id " + id);
        if (DynamoDBProvider.deleteImage(id)) {
            return String.format("[OK]: image info %s successfully deleted", id);
        } else {
            return String.format("[WARNING]: could not delete image info %s ", id);
        }
    }

}
