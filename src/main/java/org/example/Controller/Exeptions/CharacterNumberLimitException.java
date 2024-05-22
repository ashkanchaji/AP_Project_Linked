package org.example.Controller.Exeptions;

public class CharacterNumberLimitException extends Exception {
    public CharacterNumberLimitException(){
        super("The number of characters exceeds the limit");
    }
}
