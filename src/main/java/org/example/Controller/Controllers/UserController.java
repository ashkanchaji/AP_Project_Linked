package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.DAO.UserDAO;
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
        String response = getResponse();
        System.out.println(exchange.getRequestMethod());
        exchange.sendResponseHeaders(200, response.length());

        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        } catch (IOException e){
            System.out.println(response);
        }
    }

    private static String getResponse(){
        Gson gson = new Gson();
        String email = "achaji25663@gmail.com";
        String password = "ashkan1234";
        User user;
        String response;

        try {
            user = new User(email, "ashkan",
                    "chaji", password);
            UserDAO.addUserToDB(user);
            UserDAO.addUserJWT(UserController.createToken(email, password), user);
            response = gson.toJson(user);
            return response;
        } catch (InvalidPassException | InvalidEmailException e) {
            OutPut.printInvalidEmailOrPass();
            return "hgdf";
        }
    }

    public static String createToken(String email, String password){
        String subject = email.concat(":" + password);

        return JwtUtil.generateToken(subject);
    }
}
