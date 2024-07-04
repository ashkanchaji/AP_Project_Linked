package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;
import java.util.UUID;

public class Comment extends Post {

    private final String commentId;
    private final String postId; // ID of the post being commented on
    private final String repliedUser;

    public Comment(String postId, String posterID, String text, int likes, int comments, Date createdAt, int reposts, String repliedUser) throws CharacterNumberLimitException {
        super(posterID, text, likes, comments, createdAt, reposts);
        this.commentId = generateUniqueId(); // Generate unique ID for comment
        this.postId = postId;
        if (repliedUser.length() > 1250)
            throw new CharacterNumberLimitException();
        else
            this.repliedUser = repliedUser;
    }

    public Comment(String commentId, String postId, String posterID, String text, int likes, int comments, Date createdAt, int reposts, String repliedUser) throws CharacterNumberLimitException {
        super(posterID, text, likes, comments, createdAt, reposts);
        this.commentId = commentId;
        this.postId = postId;
        this.repliedUser = repliedUser;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getPostId() {
        return postId;
    }

    public String getRepliedUser() {
        return repliedUser;
    }

    @Override
    public String toString(){
        return "(Comment)" +
                "{CommentId :" + commentId +
                ", PostId :" + postId +
                ", RepliedUser :" + repliedUser +"}";
    }

    // Method to generate unique ID using UUID
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
