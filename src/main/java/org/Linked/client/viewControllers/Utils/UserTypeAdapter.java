package org.Linked.client.viewControllers.Utils;

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
        out.name("lastName").value(user.getLastName());  // corrected "lastname" to "lastName"
        out.name("password").value(user.getPassword());
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        String email = null;
        String firstName = null;
        String lastName = null;
        String password = null;

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
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        try {
            return new User(email, firstName, lastName, password);
        } catch (InvalidEmailException | InvalidPassException e) {
            throw new IOException("Error creating User object", e);
        }
    }
}
