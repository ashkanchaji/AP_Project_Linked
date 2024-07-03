package org.Linked.client.Model;

import java.util.Base64;

public class PhotoFile {
    private String postId;
    private String photoFile; // Store as Base64 string

    public PhotoFile(String postId, String photoFile) {
        this.postId = postId;
        this.photoFile = photoFile;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }

    public byte[] getPhotoFileBytes() {
        return Base64.getDecoder().decode(photoFile);
    }

    public void setPhotoFileBytes(byte[] photoFile) {
        this.photoFile = Base64.getEncoder().encodeToString(photoFile);
    }
}
