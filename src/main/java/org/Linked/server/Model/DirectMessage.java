package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;
import java.util.UUID;

public class DirectMessage extends Post {

    private String dmId; // Unique DM ID
    private String posterID;
    private String receiverID;
    private String text;
    private Date createdAt;

    public DirectMessage(String posterID, String receiverID, String text, Date createdAt) throws CharacterNumberLimitException {
        super(posterID, text, 0, 0, createdAt, 0);
        this.dmId = generateUniqueId(); // Generate unique ID
        this.posterID = posterID;
        this.receiverID = receiverID;
        if (text.length() > 1900)
            throw new CharacterNumberLimitException();
        else
            this.text = text;
        this.createdAt = createdAt;
    }

    public DirectMessage(String dmId, String posterID, String receiverID, String text, Date createdAt) throws CharacterNumberLimitException {
        super(posterID, text, 0, 0, createdAt, 0);
        this.dmId = dmId;
        this.posterID = posterID;
        this.receiverID = receiverID;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getDmId() {
        return dmId;
    }

    public String getPosterID() {
        return posterID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Method to generate unique ID using UUID
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

}
