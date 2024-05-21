package org.example.Router;

import com.sun.net.httpserver.HttpServer;
import org.example.Controller.Controllers.UserController;
import org.example.Handlers.UserHandler;
import org.example.Http.Server;

import java.io.IOException;

public class Router {
    public static void route(Server server){

        //server.get("/users", UserController::createUser);
        server.get("/users", new UserHandler());

    }
}
