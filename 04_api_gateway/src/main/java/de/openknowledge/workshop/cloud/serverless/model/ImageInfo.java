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

import java.util.Date;
import java.util.UUID;

/**
 * image information
 */
public class ImageInfo {

    private String imageId;
    private String name;
    private String title;
    private String description;
    private String owner;
    private long timestamp;

    public ImageInfo() {
        this.imageId = UUID.randomUUID().toString();
        this.timestamp = new Date().getTime();
    }

    public ImageInfo(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }

    protected void setImageId(final String imageId) {
        this.imageId = imageId;
    }

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ title.hashCode() ^ description.hashCode() ^ owner.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null
                || !(object.getClass().isAssignableFrom(getClass()) && getClass().isAssignableFrom(object.getClass()))) {
            return false;
        }
        ImageInfo imageInfo = (ImageInfo)object;
        return getImageId() != null && getImageId().equals(imageInfo.getImageId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + imageId;
    }
}