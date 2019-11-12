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
import de.openknowledge.workshop.cloud.serverless.model.CreateImageInfoRequest;
import de.openknowledge.workshop.cloud.serverless.model.ImageInfo;

/**
 * AWS lambda request handler to create image info object in dynamo db
 */
// de.openknowledge.workshop.cloud.serverless.lambda.CreateImageInfo::handleRequest
public class CreateImageInfo implements RequestHandler<CreateImageInfoRequest, ImageInfo> {

    /**
     * Stores image info in dynamo db image info table
     *
     * @param request image info wrapped via <code>CreateImageInfoRequest</code>
     * @param context aws lambda context
     * @return image info created
     */
    public ImageInfo handleRequest(CreateImageInfoRequest request, Context context) {

        LambdaLogger logger = context.getLogger();

        ImageInfo imageInfoToCreate = new ImageInfo();
        imageInfoToCreate.setTitle(request.getTitle());
        imageInfoToCreate.setName(request.getName());
        imageInfoToCreate.setDescription(request.getDescription());
        imageInfoToCreate.setOwner(request.getOwner());

        logger.log("Creating image with id " + imageInfoToCreate.getImageId());

        DynamoDBProvider.storeImageInfo(imageInfoToCreate);

        return imageInfoToCreate;
    }

}
