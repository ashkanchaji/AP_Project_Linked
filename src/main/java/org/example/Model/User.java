package org.example.Model;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.EmailValidator;
import org.example.Controller.Parsers.PasswordValidator;

import java.sql.*;
import java.util.ArrayList;


public class User extends Model{
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String additionalName;
    private String profilePicture;
    private String backgroundPicture;
    private String headline;
    private String country;
    private String city;
    private String profession;
    private Job currentJob;
    private ArrayList<Job> previousJobs;
    private ArrayList<Education> educations;


    public User(String email, String firstName, String lastName, String password) throws InvalidEmailException, InvalidPassException {
        if (EmailValidator.isValidEmail(email) && EmailValidator.isUniqueEmail(email)){
            this.email = email.toLowerCase();
        } else {
            throw new InvalidEmailException();
        }

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
