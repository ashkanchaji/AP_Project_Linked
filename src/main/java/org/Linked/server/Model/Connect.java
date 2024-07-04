package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

public class Connect extends Model {

    private String sender;
    private String receiver;
    private String notes;
    private boolean pending;

    public Connect(int type, String sender, String receiver, String notes, boolean pending) throws CharacterNumberLimitException {
        this.sender = sender;
        this.receiver = receiver;
        if (notes.length() > 500) throw new CharacterNumberLimitException();
        else this.notes = notes;
        this.pending = pending;
    }

    public Connect(String sender, String receiver, String notes, boolean pending) {
        this.sender = sender;
        this.receiver = receiver;
        this.notes = notes;
        this.pending = pending;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isPending() {
        return pending;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return "(Connect){ sender:" + sender + " notes:" + notes + " receiver:" + receiver + " pending:" + pending + " }";
    }
}
