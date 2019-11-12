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
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;

import de.openknowledge.workshop.cloud.serverless.business.ImageProcessor;
import de.openknowledge.workshop.cloud.serverless.business.ImageProcessorFactory;
import de.openknowledge.workshop.cloud.serverless.infrastructure.AwsClientProvider;

import org.apache.commons.io.FileUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

import static de.openknowledge.workshop.cloud.serverless.util.LambdaEnvironment.getEnvVarAsString;

/**
 * Lambda function to generate thumbnails from images stored in S3.
 *
 * Supported image types:
 *
 * <ul>
 *     <li>jpg</li>
 *     <li>png</li>
 * </ul>
 *
 */
// de.openknowledge.workshop.cloud.serverless.lambda.ThumbnailGenerator::handleRequest
public class ThumbnailGenerator implements RequestHandler<S3Event, String> {

    // TODO check TARGET_BUCKET_NAME name

    // AWS S3 bucket
    private final static String TARGET_BUCKET_NAME = "ok-ws-images";

    // AWS S3 "folder" for generated thumbnails
    private final static String TARGET_KEY_PREFIX = "thumbnails/";

    // environment variable to set a specific image processor
    private final static String IMAGE_PROCESSOR = "image-processor";

    // default image processor to use if no image processor is set
    private final static String DEFAULT_IMAGE_PROCESSOR = "";

    // constants for supported image suffix
    private final String JPG_TYPE = (String) "jpg";
    private final String PNG_TYPE = (String) "png";

    // constants for supported image mime types
    private final String JPG_MIME = (String) "image/jpeg";
    private final String PNG_MIME = (String) "image/png";


    // AWS S3 Client to be able to read and write to/from the S3 storage system
    private final static S3Client S3_CLIENT = AwsClientProvider.getS3Client();


    /**
     * Generates thumbnail(s) from image(s) stored in S3. Information about
     * image location(s) and image name(s) can be retrieved from the trigger event
     * of type <code>S3Event</code>
     *
     * @param s3event S3 event triggered by the S3 storage system.
     * @param context Lambda function context
     * @return S3 object key (name and location) of generated thumbnail
     */
    @Override
    public String handleRequest(S3Event s3event, Context context) {

        String thumbnailName = "UNKNOWN";

        // access context related logger
        LambdaLogger logger = context.getLogger();

        // TODO try to understand whats going on and explain it to your neighbour or to yourself ;-)
        for (S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord : s3event.getRecords()) {

            // STEP 1: extract object (image) information from S3 event

            S3EventNotification.S3Entity s3Entity = s3EventNotificationRecord.getS3();
            String eventName = s3EventNotificationRecord.getEventName();
            String bucketName = s3Entity.getBucket().getName();
            String keyName = s3Entity.getObject().getKey();

            logger.log("S3 Event received: " + eventName);
            logger.log("Bucket name: " + bucketName);
            logger.log("Object name: " + keyName);

            thumbnailName = createThumbnail(bucketName, keyName);
        }
        return thumbnailName;
    }

    /**
     * Generates a thumbnail from image stored in S3.
     *
     * @param bucketName s3 bucket name of the original image
     * @param keyName s3 key name of the original image
     * @return name of the generated thumbnail
     */
    private String createThumbnail(String bucketName, String keyName) {

        String thumbnailName = "UNKNWON";

        String objectType = retrieveImageType(keyName);
        String objectName = retrieveObjectName(keyName);

        if (!isSupportedImageType(objectType)) {
            return String.format("UNSUPPORTED FILE FORMAT: %s", objectName);
        }
        try {

            // STEP 1: access image object from S3 bucket

            // TODO create GetObjectRequest for later use with S3_CLIENT
            //      you will find the name of the bucket in bucketName
            //      you will find the name of the key in keyName
            //
            // HINT GetObjectRequest offers a builder you should make usage of.

            GetObjectRequest getObjectRequest = null;


            // create in memory image file
            String tmpName = UUID.randomUUID().toString();

            // File inMemoryFile = Files.createTempFile(objectName, objectType).toFile();
            File inMemoryFile = Files.createTempFile(objectName + tmpName, objectType).toFile();

            // fill in memory file with s3 object data from GetObjectRequest
            //  GetObjectRequest getObjectRequest = ...

            ResponseInputStream<GetObjectResponse> s3ObjectInputStream =
                    S3_CLIENT.getObject(getObjectRequest, ResponseTransformer.toInputStream());

            FileUtils.copyInputStreamToFile(s3ObjectInputStream, inMemoryFile);


            // STEP 2: resize image via image processor

            // call image processor to resize image
            // NOTE: type of image processor can be set via lambda env variable
            String requestedImageProcessor = getEnvVarAsString(IMAGE_PROCESSOR, ImageProcessorFactory.DEFAULT_IMAGE_PROCESSOR);
            ImageProcessor imageProcessor = ImageProcessorFactory.createImageProcessor(requestedImageProcessor);

            File convertedFile = imageProcessor.resizeImage(inMemoryFile, 100, 100);
            long convertedFileLength = convertedFile.length();

            // STEP 3: save resized image to target bucket
            thumbnailName = TARGET_KEY_PREFIX + "tn_" + objectName + "." + objectType;


            // TODO create a PutObjectRequest object for
            //      - with bucket TARGET_BUCKET_NAME
            //      - with key thumbnailName
            //      - with acl of type ObjectCannedACL.PUBLIC_READ
            //      - with content type of objectType
            //      - with content length of convertedFileLength
            //
            // HINT GetObjectRequest offers a builder you should make usage of.

            PutObjectRequest putObjectRequest = null;

            // TODO put object to S3 with the help of S3_CLIENT and the created PutObjectRequest object
            //
            // HINT: you can use the static method RequestBody.fromFile to transform the converted in-memory
            //       file (convertedFile) to a RequestBody object.

            // put object to S3
            PutObjectResponse putObjectResponse = null;

        } catch (IOException ex) {
            // he, what could go wrong? ;-)
        }
        return thumbnailName;
    }


    /**
     * Extracts the related file name from S3 storage key
     *
     * @param keyName S3 storage key
     * @return extracted object name
     */
    private String retrieveObjectName(String keyName) {

        // key name could be e.g. "myName.jpg" or "myFolder/myName.jpg"
        // returns "myName"
        String[] parts = keyName.split("/");
        String fileName = parts[parts.length-1];
        return fileName.substring(0, fileName.indexOf('.'));
    }

    /**
     * Extracts image type from S3 storage key
     *
     * @param keyName S3 storage key
     * @return extracted image type (e.g. jpg)
     */
    private String retrieveImageType(String keyName) {

        // key name could be e.g. "myName.jpg" or "myFolder/myName.jpg"
        // returns "jpg"
        String[] parts = keyName.split("/");
        String fileName = parts[parts.length-1];
        String imageType = fileName.substring(fileName.indexOf('.')+1);
        return imageType;
    }

    /**
     * Checks if a given image format is supported.
     *
     * @param imageType image format
     * @return true, if image format is supported
     *         false, else
     */
    boolean isSupportedImageType(String imageType) {
        return (JPG_TYPE.equals(imageType)) || (PNG_TYPE.equals(imageType));
    }
}