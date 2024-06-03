package org.server.Model;

import org.server.Controller.Exeptions.InvalidEmailException;
import org.server.Controller.Exeptions.InvalidPassException;
import org.server.Controller.Parsers.EmailValidator;
import org.server.Controller.Parsers.PasswordValidator;

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
    private String jwtToken;


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

    public User(String email, String firstName, String lastName, String password, String additionalName, String profilePicture, String backgroundPicture, String headline, String country, String city, String profession, String jwtToken) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.additionalName = additionalName;
        this.profilePicture = profilePicture;
        this.backgroundPicture = backgroundPicture;
        this.headline = headline;
        this.country = country;
        this.city = city;
        this.profession = profession;
        this.jwtToken = jwtToken;
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

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBackgroundPicture() {
        return backgroundPicture;
    }

    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public ArrayList<Job> getPreviousJobs() {
        return previousJobs;
    }

    public void setPreviousJobs(ArrayList<Job> previousJobs) {
        this.previousJobs = previousJobs;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
