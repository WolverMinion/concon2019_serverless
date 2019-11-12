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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class CloudinaryImageProcessor implements ImageProcessor {

    /**
     * Resizes an image with the help of the cloudinary cloud image service.
     *
     * @param image image file to resize the image from
     * @param width width of the resized image
     * @param height height of the resized image
     * @return file with resized image
     */
    public File resizeImage(File image, int width, int height) {

        // not implemented so far
        throw new NotImplementedException();
    }

}
