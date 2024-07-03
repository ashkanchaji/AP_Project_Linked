package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.Like;

import java.io.IOException;

public class LikeTypeAdapter extends TypeAdapter<Like> {

    @Override
    public void write(JsonWriter out, Like like) throws IOException {
        out.beginObject();
        out.name("liker").value(like.getLiker());
        out.name("liked").value(like.getLiked());
        out.name("postId").value(like.getPostId());
        out.endObject();
    }

    @Override
    public Like read(JsonReader in) throws IOException {
        String liker = null;
        String liked = null;
        String postId = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "liker":
                    liker = in.nextString();
                    break;
                case "liked":
                    liked = in.nextString();
                    break;
                case "postId":
                    postId = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Like(liker, liked, postId);
    }
}
