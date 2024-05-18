package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.JwtUtil;
import org.example.Controller.Parsers.OutPut;
import org.example.Model.User;

import java.io.IOException;
import java.io.OutputStream;

public class UserController extends Controller{
    // get all
    // get one specific entity
    // create
    // update
    // delete
    public static void createUser(HttpExchange exchange) throws IOException {
        // create user in db

        Gson gson = new Gson();
        String email = "achaji2563@gmail.com";
        String password = "ashkan1234";
        User user;
        String response;

        try {
            user = new User(email, "ashkan",
                    "chaji", password);
            user.addUserToDB();
            user.addUserJWT(UserController.createToken(email, password));
            response = gson.toJson(user);
        } catch (InvalidPassException | InvalidEmailException e) {
            OutPut.printInvalidEmailOrPass();
            return;
        }


        exchange.sendResponseHeaders(200, response.length());

        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }

    public static String createToken(String email, String password){
        String subject = email.concat(":" + password);

        return JwtUtil.generateToken(subject);
    }
}
