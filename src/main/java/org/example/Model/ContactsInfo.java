package org.example.Model;

import org.example.Controller.Exeptions.characterNumberLimitException;

import java.util.Date;

public class ContactsInfo extends Model {
    private String link;
    private String email;
    private String phoneNumber;
    private String phoneType;  //home-work-mobile
    private String address;
    private Date birthday;
    //سیاست نمایش تولد
    private String contactUs; //rah ertebati ani

    public ContactsInfo(String link, String email, String phoneNumber, String phoneType, String address, Date birthday, String contactUs) throws characterNumberLimitException {
        if (link.length() > 40)
            throw new characterNumberLimitException();
        else
            this.link = link;
        if (email.length() > 40)
            this.email = email;
        else
            throw  new characterNumberLimitException();
        if (phoneNumber.length() > 40)
            this.phoneNumber = phoneNumber;
        else
            throw  new characterNumberLimitException();
        if (phoneType.length() > 40)
            this.phoneType = phoneType;
        else
            throw  new characterNumberLimitException();
        if (address.length() > 40)
            this.address = address;
        else
            throw new characterNumberLimitException();

        this.birthday = birthday;  // in typesh string nis
        if (contactUs.length() > 40)
            this.contactUs = contactUs;
        else
            throw  new characterNumberLimitException();
    }
}
