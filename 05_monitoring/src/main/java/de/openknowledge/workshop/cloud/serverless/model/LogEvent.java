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

/**
 * Base clazz for all log events (common event)
 */
public class LogEvent {

    /*         {"id":"eventId1",
     *          "timestamp":1440442987000,
     *          "message":"[ERROR] First test message"},
    */

    private String id;
    private long timestamp;
    private String message;

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public String toString() {

        return "{" +
                " \n id: " + id +
                " \n timestamp: " + timestamp +
                " \n message: " + message +
                " \n}";

    }

}
