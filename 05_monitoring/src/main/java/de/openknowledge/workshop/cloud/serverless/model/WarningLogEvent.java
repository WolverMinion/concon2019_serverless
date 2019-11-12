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
 * Log event of type warning.
 */
public class WarningLogEvent extends LogEvent {

    public static final String WARNING_PREFIX = "[WARN] ";

    private String rawMessage;

    public WarningLogEvent() {
        super();
    }

    public WarningLogEvent(LogEvent event) {
        this.setId(event.getId());
        this.setMessage(event.getMessage());
        this.setTimestamp(event.getTimestamp());
        setRawMessage(event.getMessage());
    }

    public String getRawMessage() {
        return rawMessage;
    }

    private void setRawMessage(String message) {
        // "[WARN]:raw message
        String rawMessage = message.substring(WARNING_PREFIX.length());
        this.rawMessage = rawMessage;
    }
}
