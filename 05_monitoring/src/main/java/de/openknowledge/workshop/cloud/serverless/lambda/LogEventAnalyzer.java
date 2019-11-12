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
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent;

import de.openknowledge.workshop.cloud.serverless.model.*;
import de.openknowledge.workshop.cloud.serverless.util.GzipUtil;
import de.openknowledge.workshop.cloud.serverless.util.LogEventParser;

import java.util.Base64;

import static de.openknowledge.workshop.cloud.serverless.model.ErrorLogEvent.ERROR_PREFIX;
import static de.openknowledge.workshop.cloud.serverless.model.MetricLogEvent.METRIC_PREFIX;
import static de.openknowledge.workshop.cloud.serverless.model.WarningLogEvent.WARNING_PREFIX;

/**
 * Aws lambda listening to cloud watch events
 */
// de.openknowledge.workshop.cloud.serverless.lambda.LogEventAnalyzer::handleRequest
public class LogEventAnalyzer implements RequestHandler<CloudWatchLogsEvent, String> {

    /**
     * Handles cloud watch events in context of given event type, given by its prefix:
     * <ul>
     *     <li>METRIC_PREFIX: metric event</li>
     *     <li>ERROR_PREFIX: error event</li>
     *     <li>WARNING_PREFIX: warning event</li>
     * </ul>
     *
     * @param event cloud watch event to handle
     * @param context aws lambda context
     * @return status of event processing
     */
    public String handleRequest(CloudWatchLogsEvent event, Context context) {

        String logData;

        // extract aws logs from cloudWatch event
        // each event can contain 1..n log entries
        CloudWatchLogsEvent.AWSLogs logs = event.getAwsLogs();
        String encodedAndCompressedLogData = logs.getData();

        // log entries are base64 encoded and zipped
        // we have to unzip and decode the log entries
        try {
            // decode
            byte[] compressedLogData = Base64.getDecoder().decode(encodedAndCompressedLogData);
            // unzip
            logData = GzipUtil.decompress(compressedLogData);
        } catch (Exception ex) {
            logData = "GZIP ERROR";
        }

        // parse log data and create holder object
        // with a list of <code>LogEvent</code> objects
        LogEvents logEvents = LogEventParser.parseLogEvent(logData);

        // iterate list of log events and analyze given
        // log entry type (common, error, warning or metric)
        for (LogEvent logEvent : logEvents.getLogEvents()) {
            handleLogEvent(logEvent);
        }
        return "OK";
    }

    /**
     * Dispatches log event depending on its type to specific log event handler
     * @param logEvent log event to dispatch
     */
    private void handleLogEvent(LogEvent logEvent) {
        String message = logEvent.getMessage();
        if (message.startsWith(METRIC_PREFIX)) {
            handleMetricLogEvent(logEvent);
        } else if (message.startsWith(ERROR_PREFIX)) {
            handleErrorLogEvent(logEvent);
        } else if (message.startsWith(WARNING_PREFIX)) {
            handleWarningLogEvent(logEvent);
        } else {
            handleCommonLogEvent(logEvent);
        }
    }

    /**
     * Handles error events
     * @param event error event to handle
     */
    private void handleErrorLogEvent(LogEvent event) {
        ErrorLogEvent errorEvent = new ErrorLogEvent(event);
        System.out.println("[ERROR] msg = " + errorEvent.getRawMessage());
    }

    /**
     * Handles metric events
     * @param event metric event to handle
     */
    private void handleMetricLogEvent(LogEvent event) {
        MetricLogEvent metricEvent = new MetricLogEvent(event);
        System.out.println("[METRIC] msg = " + metricEvent.getRawMessage());
        System.out.println("   - KEY: " + metricEvent.getKey());
        System.out.println("   - VALUE: " + metricEvent.getValue());
    }

    /**
     * Handles warning events
     * @param event warning event to handle
     */
    private void handleWarningLogEvent(LogEvent event) {
        WarningLogEvent warningEvent = new WarningLogEvent(event);
        System.out.println("[WARN] msg =  " + warningEvent.getRawMessage());
    }

    /**
     * Handles common events
     * @param event common event to handle
     */
    private void handleCommonLogEvent(LogEvent event) {
        System.out.println("msg =   " + event.getMessage());
    }

}
