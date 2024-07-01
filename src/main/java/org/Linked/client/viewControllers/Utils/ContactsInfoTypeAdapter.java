package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.ContactsInfo;

import java.io.IOException;
import java.sql.Date;

public class ContactsInfoTypeAdapter extends TypeAdapter<ContactsInfo> {

    @Override
    public void write(JsonWriter out, ContactsInfo contactsInfo) throws IOException {
        out.beginObject();
        out.name("email").value(contactsInfo.getEmail());
        out.name("contactEmail").value(contactsInfo.getContactEmail());
        out.name("phoneNumber").value(contactsInfo.getPhoneNumber());
        out.name("phoneType").value(contactsInfo.getPhoneType());
        out.name("address").value(contactsInfo.getAddress());
        out.name("birthday").value(contactsInfo.getBirthday() != null ? contactsInfo.getBirthday().toString() : null);
        out.name("contactUs").value(contactsInfo.getContactUs());
        out.endObject();
    }

    @Override
    public ContactsInfo read(JsonReader in) throws IOException {
        String email = null;
        String contactEmail = null;
        String phoneNumber = null;
        String phoneType = null;
        String address = null;
        Date birthday = null;
        String contactUs = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "email":
                    email = in.nextString();
                    break;
                case "contactEmail":
                    contactEmail = in.nextString();
                    break;
                case "phoneNumber":
                    phoneNumber = in.nextString();
                    break;
                case "phoneType":
                    phoneType = in.nextString();
                    break;
                case "address":
                    address = in.nextString();
                    break;
                case "birthday":
                    String birthdayStr = in.nextString();
                    birthday = birthdayStr != null ? Date.valueOf(birthdayStr) : null;
                    break;
                case "contactUs":
                    contactUs = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new ContactsInfo(email, contactEmail, phoneNumber, phoneType, address, birthday, contactUs);
    }
}
