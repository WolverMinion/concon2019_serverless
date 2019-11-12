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
 * Factory to generate an <code></code>ImageProcessor</code> of the requested type.
 *
 * Allowed types are
 * <ul>
 *     <li>internal</li>
 *     <li>cloudinary</li>
 * </ul>
 *
 */
public class ImageProcessorFactory {

    private static final String INTERNAL_IMAGE_PROCESSOR = "internal";
    private static final String CLOUDINARY_IMAGE_PROCESSOR = "cloudinary";
    public static final String DEFAULT_IMAGE_PROCESSOR = INTERNAL_IMAGE_PROCESSOR;

    /**
     * default constructor
     */
    private ImageProcessorFactory() {
        // empty by default
    }

    /**
     * Creates image processor of type DEFAULT
     * @return image processor
     */
    public static ImageProcessor createImageProcessor() {
        return createImageProcessor(DEFAULT_IMAGE_PROCESSOR);
    }

    /**
     * Creates image processor of given image processor type
     * @param imageProcessorType type of image processor
     * @return image processor
     */
    public static ImageProcessor createImageProcessor(String imageProcessorType) {
        switch (imageProcessorType) {
            case CLOUDINARY_IMAGE_PROCESSOR:
                return new CloudinaryImageProcessor();
            case INTERNAL_IMAGE_PROCESSOR:
                return new DefaultImageProcessor();
            default:
                return new DefaultImageProcessor();
        }
    }
}
