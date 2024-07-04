package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.Comment;

import java.io.IOException;
import java.sql.Date;

public class CommentTypeAdapter extends TypeAdapter<Comment> {

    @Override
    public void write(JsonWriter out, Comment comment) throws IOException {
        out.beginObject();
        out.name("commentId").value(comment.getCommentId());
        out.name("postId").value(comment.getPostId());
        out.name("posterID").value(comment.getUserId());
        out.name("text").value(comment.getText());
        out.name("likes").value(comment.getLikes());
        out.name("comments").value(comment.getComments());
        out.name("createdAt").value(comment.getCreatedAt().toString());
        out.name("reposts").value(comment.getReposts());
        out.name("repliedUser").value(comment.getRepliedUser());
        if (comment.getByteFilePath() != null) {
            out.name("byteFilePath").value(comment.getByteFilePath());
        }
        out.endObject();
    }

    @Override
    public Comment read(JsonReader in) throws IOException {
        String commentId = null;
        String postId = null;
        String posterID = null;
        String text = null;
        int likes = 0;
        int comments = 0;
        Date createdAt = null;
        int reposts = 0;
        String repliedUser = null;
        String byteFilePath = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "commentId":
                    commentId = in.nextString();
                    break;
                case "postId":
                    postId = in.nextString();
                    break;
                case "posterID":
                    posterID = in.nextString();
                    break;
                case "text":
                    text = in.nextString();
                    break;
                case "likes":
                    likes = in.nextInt();
                    break;
                case "comments":
                    comments = in.nextInt();
                    break;
                case "createdAt":
                    createdAt = Date.valueOf(in.nextString());
                    break;
                case "reposts":
                    reposts = in.nextInt();
                    break;
                case "repliedUser":
                    repliedUser = in.nextString();
                    break;
                case "byteFilePath":
                    byteFilePath = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        try {
            return new Comment(commentId, postId, posterID, text, likes, comments, createdAt, reposts, repliedUser);
        } catch (CharacterNumberLimitException e) {
            throw new IOException("Character limit exceeded for repliedUser", e);
        }
    }
}
