package org.Linked.client.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class ContactsInfo extends Model {
    private String email;
    private String contactEmail;
    private String phoneNumber;
    private String phoneType;  //home-work-mobile
    private String address;
    private Date birthday;
    //سیاست نمایش تولد
    private String contactUs; //rah ertebati ani

    public ContactsInfo(int type, String email, String contactEmail, String phoneNumber, String phoneType, String address, Date birthday, String contactUs) throws CharacterNumberLimitException {
        if (contactEmail.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.contactEmail = contactEmail;
        if (email.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.email = email;
        if (phoneNumber.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.phoneNumber = phoneNumber;
        if (phoneType.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.phoneType = phoneType;
        if (address.length() > 220)
            throw new CharacterNumberLimitException();
        else
            this.address = address;

        this.birthday = birthday;  // in typesh string nis
        if (contactUs.length() > 40)
            throw new CharacterNumberLimitException();
        else
            this.contactUs = contactUs;
    }

    public ContactsInfo(String email, String contactEmail, String phoneNumber, String phoneType, String address, Date birthday, String contactUs) {
        this.email = email;
        this.contactEmail = contactEmail;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.address = address;
        this.birthday = birthday;
        this.contactUs = contactUs;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
