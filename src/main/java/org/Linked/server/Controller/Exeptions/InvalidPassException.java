package org.Linked.server.Controller.Exeptions;

public class InvalidPassException extends Exception{
    public InvalidPassException(){
        super("Invalid Password.");
    }
}
