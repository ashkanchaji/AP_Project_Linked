package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.Post;

import java.io.IOException;
import java.sql.Date;

public class PostTypeAdapter extends TypeAdapter<Post> {

    @Override
    public void write(JsonWriter out, Post post) throws IOException {
        out.beginObject();
        out.name("posterID").value(post.getUserId());
        out.name("text").value(post.getText());
        out.name("likes").value(post.getLikes());
        out.name("comments").value(post.getComments());
        out.name("createdAt").value(post.getCreatedAt().toString());
        out.name("reposts").value(post.getReposts());
        out.name("byteFilePath").value(post.getByteFilePath());
        out.endObject();
    }

    @Override
    public Post read(JsonReader in) throws IOException {
        String posterID = null;
        String text = null;
        int likes = 0;
        int comments = 0;
        Date createdAt = null;
        int reposts = 0;
        String byteFilePath = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
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
                case "byteFilePath":
                    byteFilePath = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Post(posterID, text, likes, comments, createdAt, reposts, byteFilePath);
    }
}
