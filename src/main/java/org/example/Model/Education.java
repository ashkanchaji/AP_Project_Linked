package org.example.Model;

import org.example.Controller.Exeptions.characterNumberLimitException;

public class Education {
    private String collegeName;
    private String major;
    // field tarikh vorod khorojesh moonde
    private String grade;  // dalili bara int boodanesh nemibinam felan
    private String activitiesInfo;
    private String skills; // ino fek konam bayad arraylist bezanim.
    private String additionalInfo;
    public Education(String collegeName , String major , String grade , String activitiesInfo , String skills
     , String additionalInfo) throws characterNumberLimitException {
        if (collegeName.length() > 40)
            throw  new characterNumberLimitException();
        else
            this.collegeName = collegeName;
        if (major.length() > 40)
            throw  new characterNumberLimitException();
        else
            this.major = major;
        if (grade.length() > 40)
            throw new characterNumberLimitException();
        else
            this.grade = grade;
        if (activitiesInfo.length() > 500)
            throw new characterNumberLimitException();
        else
            this.activitiesInfo = activitiesInfo;
        if (additionalInfo.length() > 1000)
            throw  new characterNumberLimitException();
        else
            this.additionalInfo = additionalInfo;
        //skill hanooz moonde
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getActivitiesInfo() {
        return activitiesInfo;
    }

    public String getSkills() {
        return skills;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
