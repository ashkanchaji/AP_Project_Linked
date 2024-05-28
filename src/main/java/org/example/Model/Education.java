package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;
import java.util.ArrayList;

public class  Education extends Model{
    private String email;
    private String collegeName;
    private String major;
    private Date enterYear; // these are mysql date wrapper types
    private Date exitYear; //
    private String grade;
    private String activitiesInfo;
    private ArrayList<String> skills;
    private String additionalInfo;
    public Education(int type, String email, String collegeName , String major ,Date enterYear, Date exitYear ,
                     String grade , String activitiesInfo , ArrayList<String> skills
                    , String additionalInfo) throws CharacterNumberLimitException {
        this.email = email;
        if (collegeName.length() > 40)
            throw  new CharacterNumberLimitException();
        else
            this.collegeName = collegeName;
        if (major.length() > 40)
            throw  new CharacterNumberLimitException();
        else
            this.major = major;
        if (grade.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.grade = grade;
        if (activitiesInfo.length() > 500)
            throw new CharacterNumberLimitException();
        else
            this.activitiesInfo = activitiesInfo;
        if (skills != null && !skills.isEmpty()){
            for (String skill : skills){
                if (skill.length() > 40)
                    throw new CharacterNumberLimitException();
            }
        }
        else {
            this.skills = skills;
        }
        if (additionalInfo.length() > 1000)
            throw  new CharacterNumberLimitException();
        else
            this.additionalInfo = additionalInfo;

        this.enterYear = enterYear;
        this.exitYear = exitYear;
    }

    public Education(String email, String collegeName, String major, Date enterYear, Date exitYear, String grade, String activitiesInfo, ArrayList<String> skills, String additionalInfo) {
        this.email = email;
        this.collegeName = collegeName;
        this.major = major;
        this.enterYear = enterYear;
        this.exitYear = exitYear;
        this.grade = grade;
        this.activitiesInfo = activitiesInfo;
        this.skills = skills;
        this.additionalInfo = additionalInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(Date enterYear) {
        this.enterYear = enterYear;
    }

    public Date getExitYear() {
        return exitYear;
    }

    public void setExitYear(Date exitYear) {
        this.exitYear = exitYear;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActivitiesInfo() {
        return activitiesInfo;
    }

    public void setActivitiesInfo(String activitiesInfo) {
        this.activitiesInfo = activitiesInfo;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
