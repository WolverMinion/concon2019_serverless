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
 * Log event of type metric.
 */
public class MetricLogEvent extends LogEvent {

    public static final String METRIC_PREFIX = "[METRIC] ";

    private String rawMessage;
    private String key;
    private double value;

    public MetricLogEvent() {
        super();
    }

    public MetricLogEvent(LogEvent event) {
        this.setId(event.getId());
        this.setMessage(event.getMessage());
        this.setTimestamp(event.getTimestamp());
        setMetricKeyAndValue(event.getMessage());
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    private void setMetricKeyAndValue(String message) {
        // "[METRIC]:metric-key|metric-value
        String rawMessage = message.substring(METRIC_PREFIX.length());
        String key = rawMessage.substring(0, rawMessage.indexOf("|"));
        String value = rawMessage.substring(rawMessage.indexOf("|")+1);

        this.rawMessage = rawMessage;
        this.key = key;
        this.value = Double.valueOf(value);
    }
}