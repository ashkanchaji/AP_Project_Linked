package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.PhotoFile;

import java.io.IOException;

public class PhotoFileTypeAdapter extends TypeAdapter<PhotoFile> {

    @Override
    public void write(JsonWriter out, PhotoFile photoFile) throws IOException {
        out.beginObject();
        out.name("postId").value(photoFile.getPostId());
        out.name("photoFile").value(photoFile.getPhotoFile());
        out.endObject();
    }

    @Override
    public PhotoFile read(JsonReader in) throws IOException {
        String postId = null;
        String photoFile = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "postId":
                    postId = in.nextString();
                    break;
                case "photoFile":
                    photoFile = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new PhotoFile(postId, photoFile);
    }
}
