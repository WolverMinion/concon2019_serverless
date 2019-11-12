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

import de.openknowledge.workshop.cloud.serverless.model.HelloWorldRequest;
import de.openknowledge.workshop.cloud.serverless.model.HelloWorldResponse;
import de.openknowledge.workshop.cloud.serverless.service.GreetingService;

/**
 * Simplest aws lambda function class possible.
 */
// de.openknowledge.workshop.cloud.serverless.lambda.HelloWorld::greet
public class HelloWorld {

    /**
     * Converts a name (first name, last name) into a greeting
     *
     * @param name Request object of type <code>HelloWorldRequest</code> with first name and last name
     * @param context Lambda function context object of type <code>Context</code>
     * @return Greeting string wrapped inside a response object of type <code>HelloWorldResponse</code>
     */
    public static HelloWorldResponse greet(HelloWorldRequest name, Context context) {
        LambdaLogger logger = context.getLogger();

        String firstName = name.getFirstName();
        String lastName = name.getLastName();

        String response = GreetingService.greet(firstName, lastName);

        logger.log(firstName);
        logger.log(lastName);
        logger.log(response);
        logger.log(System.getenv().toString());

        return new HelloWorldResponse(response);
    }
}

