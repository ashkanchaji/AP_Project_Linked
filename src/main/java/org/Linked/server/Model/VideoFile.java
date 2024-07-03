package org.Linked.server.Model;

import java.util.Base64;

public class VideoFile {
    private String postId;
    private String videoFile; // Store as Base64 string

    public VideoFile(String postId, String videoFile) {
        this.postId = postId;
        this.videoFile = videoFile;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public byte[] getVideoFileBytes() {
        return Base64.getDecoder().decode(videoFile);
    }

    public void setVideoFileBytes(byte[] videoFile) {
        this.videoFile = Base64.getEncoder().encodeToString(videoFile);
    }
}
