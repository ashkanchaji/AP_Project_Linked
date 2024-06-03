package org.server.Controller.Exeptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(){
        super("Invalid Email.");
    }
}
