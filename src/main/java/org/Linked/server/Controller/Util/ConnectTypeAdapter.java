package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.Connect;

import java.io.IOException;

public class ConnectTypeAdapter extends TypeAdapter<Connect> {

    @Override
    public void write(JsonWriter out, Connect connect) throws IOException {
        out.beginObject();
        out.name("sender").value(connect.getSender());
        out.name("receiver").value(connect.getReceiver());
        out.name("notes").value(connect.getNotes());
        out.name("pending").value(connect.isPending());
        out.endObject();
    }

    @Override
    public Connect read(JsonReader in) throws IOException {
        String sender = null;
        String receiver = null;
        String notes = null;
        boolean pending = false;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "sender":
                    sender = in.nextString();
                    break;
                case "receiver":
                    receiver = in.nextString();
                    break;
                case "notes":
                    notes = in.nextString();
                    break;
                case "pending":
                    pending = in.nextBoolean();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Connect(sender, receiver, notes, pending);
    }
}
