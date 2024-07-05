package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.PDFFile;

import java.io.IOException;

public class PDFFileTypeAdapter extends TypeAdapter<PDFFile> {

    @Override
    public void write(JsonWriter out, PDFFile pdfFile) throws IOException {
        out.beginObject();
        out.name("postId").value(pdfFile.getPostId());
        out.name("pdfFile").value(pdfFile.getPdfFile());
        out.endObject();
    }

    @Override
    public PDFFile read(JsonReader in) throws IOException {
        String postId = null;
        String pdfFile = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "postId":
                    postId = in.nextString();
                    break;
                case "pdfFile":
                    pdfFile = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new PDFFile(postId, pdfFile);
    }
}
