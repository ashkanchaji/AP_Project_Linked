package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.DirectMessage;

import java.io.IOException;
import java.sql.Date;

public class DirectMessageTypeAdapter extends TypeAdapter<DirectMessage> {

    @Override
    public void write(JsonWriter out, DirectMessage directMessage) throws IOException {
        out.beginObject();
        out.name("dmId").value(directMessage.getDmId());
        out.name("posterID").value(directMessage.getPosterID());
        out.name("receiverID").value(directMessage.getReceiverID());
        out.name("text").value(directMessage.getText());
        out.name("createdAt").value(directMessage.getCreatedAt().toString()); // Assuming createdAt is always non-null
        out.endObject();
    }

    @Override
    public DirectMessage read(JsonReader in) throws IOException {
        String dmId = null;
        String posterID = null;
        String receiverID = null;
        String text = null;
        Date createdAt = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "dmId":
                    dmId = in.nextString();
                    break;
                case "posterID":
                    posterID = in.nextString();
                    break;
                case "receiverID":
                    receiverID = in.nextString();
                    break;
                case "text":
                    text = in.nextString();
                    break;
                case "createdAt":
                    createdAt = Date.valueOf(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        try {
            if (dmId == null) {
                return new DirectMessage(posterID, receiverID, text, createdAt);
            } else {
                return new DirectMessage(dmId, posterID, receiverID, text, createdAt);
            }
        } catch (CharacterNumberLimitException e) {
            throw new IOException("Text length exceeded character limit", e);
        }
    }
}
