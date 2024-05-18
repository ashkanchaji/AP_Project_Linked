package org.example.Controller.Exeptions;

public class characterNumberLimitException extends Exception {
    public characterNumberLimitException(){
        super("The number of characters exceeds the limit");
    }
}
