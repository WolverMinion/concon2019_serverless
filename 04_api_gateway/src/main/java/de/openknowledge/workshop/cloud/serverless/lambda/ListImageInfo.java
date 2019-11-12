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
import de.openknowledge.workshop.cloud.serverless.model.ListImagesRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * AWS lambda request handler to list image info objects from dynamo db
 */
// de.openknowledge.workshop.cloud.serverless.lambda.ListImageInfo::handleRequest
public class ListImageInfo implements RequestHandler<ListImagesRequest, List<ImageInfo>> {


    private LambdaLogger logger;

    /**
     * List all image (of a given owner)
     *
     * @param request Input parameter of the lambda function representing the image owner
     * @param context Related context of the lambda function.
     * @return List of <code>ImageInfo</code> objects owned by owner
     */
    @Override
    public List<ImageInfo> handleRequest(ListImagesRequest request, Context context) {

        // initialize return value
        List<ImageInfo> imageList = new ArrayList<>();

        String owner = request.getImageOwner();

        // access context related logger
        logger = context.getLogger();

        if (owner != null && !"".equals(owner)) {
            imageList = DynamoDBProvider.findAllOf(owner);
        } else {
            imageList = DynamoDBProvider.findAll();
        }

        logger.log("Image information found: " + imageList.size());

        return imageList;
    }


}