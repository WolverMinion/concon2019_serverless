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
package de.openknowledge.workshop.cloud.serverless.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple Greetings generator
 */
public class Greeter {

    // Initialize the Log4j logger.
    static final Logger logger = LogManager.getLogger(Greeter.class);

    /**
     * Generates greeting string from first name and last name
     *
     * @param firstName first name
     * @param lastName first name
     * @return greeting for the given name
     */
    public static String greet(String firstName, String lastName) {

        if (firstName==null ) {
            logger.error("[ERROR] firstName must not be null!");
            throw new IllegalArgumentException("[BadRequest] first name must not be null.");
        }

        if (lastName==null ) {
            logger.error("[ERROR] lastName must not be null!");
            throw new IllegalArgumentException("[BadRequest] last name must not be null.");
        }

        return String.format("Hello, %s %s! i am pleased to meet you.", firstName, lastName);
    }
}
