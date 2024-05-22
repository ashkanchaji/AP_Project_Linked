package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.util.Date;

public class ContactsInfo extends Model {
    private String link;
    private String email;
    private String phoneNumber;
    private String phoneType;  //home work mobile
    private String address;
    private Date birthday;
    //سیاست نمایش تولد
    private String contactUs; //rah ertebati ani

    public ContactsInfo(String link, String email, String phoneNumber, String phoneType, String address, Date birthday, String contactUs) throws CharacterNumberLimitException {
        if (link.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.link = link;
        if (email.length() > 40)
            this.email = email;
        else
            throw  new CharacterNumberLimitException();
        if (phoneNumber.length() > 40)
            this.phoneNumber = phoneNumber;
        else
            throw  new CharacterNumberLimitException();
        if (phoneType.length() > 40)
            this.phoneType = phoneType;
        else
            throw  new CharacterNumberLimitException();
        if (address.length() > 40)
            this.address = address;
        else
            throw new CharacterNumberLimitException();

        this.birthday = birthday;  // in typesh string nis
        if (contactUs.length() > 40)
            this.contactUs = contactUs;
        else
            throw  new CharacterNumberLimitException();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }
}
