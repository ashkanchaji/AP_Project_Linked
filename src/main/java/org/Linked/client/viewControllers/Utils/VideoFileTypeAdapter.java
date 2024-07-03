package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.VideoFile;

import java.io.IOException;

public class VideoFileTypeAdapter extends TypeAdapter<VideoFile> {

    @Override
    public void write(JsonWriter out, VideoFile videoFile) throws IOException {
        out.beginObject();
        out.name("postId").value(videoFile.getPostId());
        out.name("videoFile").value(videoFile.getVideoFile());
        out.endObject();
    }

    @Override
    public VideoFile read(JsonReader in) throws IOException {
        String postId = null;
        String videoFile = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "postId":
                    postId = in.nextString();
                    break;
                case "videoFile":
                    videoFile = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new VideoFile(postId, videoFile);
    }
}
