package org.example.Model;

import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.PasswordValidator;

public class User extends Model{
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public User(String email, String firstName, String lastName, String password) throws InvalidPassException {
        this.email = email; // *** needs validation checking ***

        if (PasswordValidator.containsNumsAndChars(password) && PasswordValidator.checkPasswordLength(password)){
            this.password = password;
        } else {
            throw new InvalidPassException();
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
