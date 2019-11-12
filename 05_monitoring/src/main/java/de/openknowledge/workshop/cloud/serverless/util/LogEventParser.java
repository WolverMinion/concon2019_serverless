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
package de.openknowledge.workshop.cloud.serverless.util;

import com.google.gson.Gson;
import de.openknowledge.workshop.cloud.serverless.model.LogEvents;

/**
 * Parser for json based log events
 */
public class LogEventParser {

    /**
     * Parses json log group representation
     * @param jsonLogData json log group representation to parse
     * @return log events representing a group of related log events
     */
    public static LogEvents parseLogEvent(String jsonLogData) {
        Gson gson = new Gson();
        LogEvents logEvents = gson.fromJson(jsonLogData, LogEvents.class);
        return logEvents;
    }
}
