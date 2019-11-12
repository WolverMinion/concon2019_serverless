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

/**
 * Validator for sensor data.
 */
public class SensorDataValidator {

     /**
     * Checks if a sensor data value exceeds a given threshold.
     *
     * @param lowerLimit lower limit of threshold
     * @param upperLimit upper limit of threshold
     * @return true, if threshold is exceeded
     *         false, else
     */
    public static boolean isInRange(double value, double lowerLimit, double upperLimit) {
        return value > lowerLimit && value < upperLimit;
    }


}
