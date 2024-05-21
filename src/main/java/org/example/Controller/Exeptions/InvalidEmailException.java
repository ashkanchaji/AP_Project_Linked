package org.example.Controller.Exeptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(){
        super("Invalid Email.");
    }
}
