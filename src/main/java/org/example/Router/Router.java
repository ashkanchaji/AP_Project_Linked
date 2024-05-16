package org.example.Router;

import org.example.Controller.Controllers.UserController;
import org.example.Http.Server;

public class Router {
    public static void route(Server server){

        server.get("/users", UserController::creatUser);

    }
}
