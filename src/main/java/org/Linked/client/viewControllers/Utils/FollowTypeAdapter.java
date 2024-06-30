package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.Follow;

import java.io.IOException;

public class FollowTypeAdapter extends TypeAdapter<Follow> {

    @Override
    public void write(JsonWriter out, Follow follow) throws IOException {
        out.beginObject();
        out.name("follower").value(follow.getFollower());
        out.name("following").value(follow.getFollowing());
        out.endObject();
    }

    @Override
    public Follow read(JsonReader in) throws IOException {
        String follower = null;
        String following = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "follower":
                    follower = in.nextString();
                    break;
                case "following":
                    following = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Follow(follower, following);
    }
}
