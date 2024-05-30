package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

public class Connect {

    private  String sender;

    private String receiver;

    private String notes;

    public Connect(String sender , String receiver , String notes) throws CharacterNumberLimitException {
        this.sender = sender;
        this.receiver = receiver;
        if (notes.length() > 500)
            throw  new CharacterNumberLimitException();
        else
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

    @Override
    public String toString(){
        return "(Connect)" +
                "{" + sender + " make connection with " + notes + " }";
    }

}

