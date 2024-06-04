package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.util.ArrayList;

public class Job {
    private String email;
    private String name;
    private String location;
    private String additionalInfo;
    private ArrayList<String> skills;

    public Job(int type, String email, String name, String location, String additionalInfo, ArrayList<String> skills) throws CharacterNumberLimitException {
        if (email.length() > 45)
            throw new CharacterNumberLimitException();
        this.email = email;

        if (name.length() > 40)
            throw new CharacterNumberLimitException();
        this.name = name;

        this.location = location;

        if (additionalInfo.length() > 1000)
            throw new CharacterNumberLimitException();
        this.additionalInfo = additionalInfo;

        this.skills = skills;
    }

    public Job(String email, String name, String location, String additionalInfo, ArrayList<String> skills) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.additionalInfo = additionalInfo;
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}
