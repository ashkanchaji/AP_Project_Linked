package org.server.Controller.Exeptions;

public class InvalidPassException extends Exception{
    public InvalidPassException(){
        super("Invalid Password.");
    }
}
