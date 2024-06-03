package org.server.Model;

import org.server.Controller.Exeptions.CharacterNumberLimitException;

public class Connect extends Model {

    private  String sender;

    private String receiver;

    private String notes;

    public Connect(int type , String sender , String receiver , String notes) throws CharacterNumberLimitException {
        this.sender = sender;
        this.receiver = receiver;
        if (notes.length() > 500)
            throw  new CharacterNumberLimitException();
        else
            this.notes = notes;
    }

    public Connect(String sender , String receiver , String notes){
        this.sender = sender;
        this.receiver = receiver;
        this.notes = notes;
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

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        return "(Connect)" +
                "{ sender:" + sender+
                " notes:" + notes +
                "receiver :" + receiver;
    }

}

