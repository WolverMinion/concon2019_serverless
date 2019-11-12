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
import de.openknowledge.workshop.cloud.serverless.model.UpdateImageInfoRequest;

import java.util.Date;

/**
 * AWS lambda request handler to update image info object in dynamo db
 */
//de.openknowledge.workshop.cloud.serverless.lambda.UpdateImageInfo::handleRequest
public class UpdateImageInfo implements RequestHandler<UpdateImageInfoRequest, String> {


    /**
     * Updates image info in dynamo db image info table
     *
     * @param request image info wrapped via <code>UpdateImageInfoRequest</code>
     * @param context aws lambda context
     * @return status of request
     */
    public String handleRequest(UpdateImageInfoRequest request, Context context) {

        // access context related logger
        LambdaLogger logger = context.getLogger();

        String id = request.getImageId();

        ImageInfo imageInfo = DynamoDBProvider.readImageInfo(id);

        if (imageInfo != null) {

            imageInfo.setTimestamp(new Date().getTime());
            imageInfo.setOwner(request.getOwner());
            imageInfo.setDescription(request.getDescription());
            imageInfo.setTitle(request.getTitle());
            imageInfo.setName(request.getName());

            logger.log("Update image info for image with id " + id);
            DynamoDBProvider.storeImageInfo(imageInfo);
            logger.log("Update image info ... DONE");
            return "ok";
        } else {
            return "Could not find image. Unknown image id " + id;
        }
    }
}