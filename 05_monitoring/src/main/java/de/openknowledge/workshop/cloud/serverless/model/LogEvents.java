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
package de.openknowledge.workshop.cloud.serverless.model;

import java.util.List;

/**
 * Log event group
 */
public class LogEvents {

    private String messageType;
    private String owner;
    private String logGroup;
    private String logStream;
    private List<String> subscriptionFilters;
    private List<LogEvent> logEvents;


    public String toString() {

        return "{" +
               " \n messageType: " + messageType +
               " \n owner: " + owner +
               " \n logGroup: " + logGroup +
               " \n logSteam: " + logStream +
               " \n subscriptionFilters: " + subscriptionFilters +
               " \n logEvents: " + logEvents +
               " \n}";

    }

    public String getMessageType() {
        return messageType;
    }

    public String getOwner() {
        return owner;
    }

    public String getLogGroup() {
        return logGroup;
    }

    public String getLogStream() {
        return logStream;
    }

    public List<String> getSubscriptionFilters() {
        return subscriptionFilters;
    }

    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    /**
     *
     * {
     *     "messageType":"DATA_MESSAGE",
     *     "owner":"123456789123",
     *     "logGroup":"testLogGroup",
     *     "logStream":"testLogStream",
     *     "subscriptionFilters":["testFilter"],
     *     "logEvents": [
     *         {"id":"eventId1",
     *          "timestamp":1440442987000,
     *          "message":"[ERROR] First test message"},
     *         {"id":"eventId2",
     *          "timestamp":1440442987001,
     *          "message":"[ERROR] Second test message"}
     *     ]}
     */


}
