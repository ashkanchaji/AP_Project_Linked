package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.util.ArrayList;

public class Job extends Model{
    private String email;
    private String name;
    //نوع اشتغال : نمام وثت پاره وقت ، خود-اشتغالی، فریلنس، قراردادی، کارآموزی، کارآموز با حقوق، فصلی
    private String location;
    //نوع محل کار )چندگزینهای شامل در محل، هیبرید، دورکاری
    //تاریخ شروع و پایان کار شامل ماه و سال )باید از
    //picker date استفاده شود(
    private String additionalInfo;
    private ArrayList<String> skills;
    //اطلاع رسانی تغییرات پروفایل به شبکه ارتباطی فرد )چک باکس(


    public Job(String email, String name, String location, String additionalInfo, ArrayList<String> skills) throws CharacterNumberLimitException {
        if (email.length() > 40)
            throw  new CharacterNumberLimitException();
        else
            this.email = email;
        this.name = name;
        this.location = location;
        if (additionalInfo.length() > 1000)
            throw new CharacterNumberLimitException();
        else
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
}
