package org.Linked.server.Handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.UserController;
import org.Linked.server.Controller.DAO.UserDAO;
import org.Linked.server.Controller.Parsers.JwtUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        try {
            switch (method) {
                case "POST":
                    JsonObject jsonObject = JsonParser.parseString(readRequestBody(exchange)).getAsJsonObject();
                    String email = jsonObject.get("email").getAsString();
                    String password = jsonObject.get("password").getAsString();

                    String userDBJson = UserController.getUser(email);
                    if (userDBJson == null) {
                        statusCode.set(401);
                        return "User not found.";
                    }

                    jsonObject = JsonParser.parseString(userDBJson).getAsJsonObject();
                    if (!password.equals(jsonObject.get("password").getAsString())){
                        statusCode.set(401);
                        return "Invalid password.";
                    }

                    String userToken = JwtUtil.generateJwtToken(email, password);
                    System.out.println(userToken);

                    UserDAO userDAO = new UserDAO();
                    userDAO.addUserJWT(userToken, email);

                    response = userToken;
                    break;
                default:
                    response = "Unsupported method";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Internal Server Error";
        }

        return response;
    }

}
