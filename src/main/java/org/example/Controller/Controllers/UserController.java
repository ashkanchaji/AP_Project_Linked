package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import io.jsonwebtoken.Claims;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.JwtUtil;
import org.example.Model.User;

import java.io.IOException;
import java.io.OutputStream;

public class UserController extends Controller{
    // get all
    // get one specific entity
    // create
    // update
    // delete
    public static void creatUser(HttpExchange exchange) throws IOException {
        // create user in db

        Gson gson = new Gson();
        User user;

        try {
            user = new User("achaji2563@gmail.com", "ashkan",
                    "chaji", "ashkan1234");
        }catch (InvalidPassException e) {
            throw new RuntimeException(e);
        }


        String response = gson.toJson(user);
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
