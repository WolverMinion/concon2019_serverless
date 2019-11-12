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

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/**
 * Default implementation of the image processor.
 *
 * This implementation uses only simple java awt and java 2d library functions.
 */
public class DefaultImageProcessor implements ImageProcessor {

    /**
     * Resizes an image with the help of java awt and 2d libraries.
     *
     * @param image image file to resize the image from
     * @param width width of the resized image
     * @param height height of the resized image
     * @return file with resized image
     */
    public File resizeImage(File image, int width, int height) {

        File resizedImageFile;

        try {

            BufferedImage srcImage = ImageIO.read(image);
            BufferedImage resizedImage =  resizeImage(srcImage, width, height);

            // Re-encode image to target format
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // ImageIO.write(resizedImage, imageType, os);
            ImageIO.write(resizedImage, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            resizedImageFile = Files.createTempFile("resizedImage", "jpg").toFile();
            FileUtils.copyInputStreamToFile(is, resizedImageFile);

        } catch (IOException ex) {
            // what could go wring?
            resizedImageFile = image;
        }

       return resizedImageFile;
    }


    /**
     * Generates a <code>BufferedImage</code> representing a resized image.
     *
     * @param image image file to resize the image from
     * @param width width of the resized image
     * @param height height of the resized image
     * @return buffed image representing the resized image
     */
    private static BufferedImage resizeImage(final Image image, int width, int height) {

        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
