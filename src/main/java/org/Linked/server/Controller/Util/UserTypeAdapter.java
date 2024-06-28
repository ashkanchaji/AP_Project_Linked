package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.InvalidEmailException;
import org.Linked.server.Controller.Exeptions.InvalidPassException;
import org.Linked.server.Model.User;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<User> {

    @Override
    public void write(JsonWriter out, User user) throws IOException {
        out.beginObject();
        out.name("email").value(user.getEmail());
        out.name("firstName").value(user.getFirstName());
        out.name("lastName").value(user.getLastName());
        out.name("password").value(user.getPassword());
        out.name("additionalName").value(user.getAdditionalName());
        out.name("profilePicture").value(user.getProfilePicture());
        out.name("backgroundPicture").value(user.getBackgroundPicture());
        out.name("headline").value(user.getHeadline());
        out.name("country").value(user.getCountry());
        out.name("city").value(user.getCity());
        out.name("profession").value(user.getProfession());
        out.name("jwtToken").value(user.getJwtToken());
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        String email = null;
        String firstName = null;
        String lastName = null;
        String password = null;
        String additionalName = null;
        String profilePicture = null;
        String backgroundPicture = null;
        String headline = null;
        String country = null;
        String city = null;
        String profession = null;
        String jwtToken = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "email":
                    email = in.nextString();
                    break;
                case "firstName":
                    firstName = in.nextString();
                    break;
                case "lastName":
                    lastName = in.nextString();
                    break;
                case "password":
                    password = in.nextString();
                    break;
                case "additionalName":
                    additionalName = in.nextString();
                    break;
                case "profilePicture":
                    profilePicture = in.nextString();
                    break;
                case "backgroundPicture":
                    backgroundPicture = in.nextString();
                    break;
                case "headline":
                    headline = in.nextString();
                    break;
                case "country":
                    country = in.nextString();
                    break;
                case "city":
                    city = in.nextString();
                    break;
                case "profession":
                    profession = in.nextString();
                    break;
                case "jwtToken":
                    jwtToken = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        try {
            // Try to use the constructor with additional fields first
            return new User(email, firstName, lastName, password, additionalName, profilePicture, backgroundPicture, headline, country, city, profession, jwtToken);
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                // If that fails, fall back to the basic constructor
                return new User(email, firstName, lastName, password);
            } catch (InvalidEmailException | InvalidPassException e2) {
                throw new IOException("Error creating User object", e2);
            }
        }
    }
}
