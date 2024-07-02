package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;
import java.util.UUID;

public class Post extends Model {

    private String postId; // Unique post ID

    private String posterID;
    private String text;
    private int likes;
    private int comments;
    private Date createdAt;
    private int reposts;
    private String byteFilePath;

    public Post(String posterID, String text, int likes, int comments, Date createdAt, int reposts) throws CharacterNumberLimitException {
        this.postId = generateUniqueId(); // Generate unique ID
        this.posterID = posterID;
        if (text.length() > 3000)
            throw new CharacterNumberLimitException();
        else
            this.text = text;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.reposts = reposts;
    }

    public Post(String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath) {
        this.postId = generateUniqueId(); // Generate unique ID
        this.posterID = posterID;
        this.text = text;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.reposts = reposts;
        this.byteFilePath = byteFilePath;
    }

    public Post(String postId, String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath) {
        this.postId = postId;
        this.posterID = posterID;
        this.text = text;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.reposts = reposts;
        this.byteFilePath = byteFilePath;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return posterID;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getReposts() {
        return reposts;
    }

    public String getByteFilePath() {
        return byteFilePath;
    }

    // Method to generate unique ID using UUID
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

}
