package org.example.Router;

import org.example.Handlers.EducationHandler;
import org.example.Handlers.JobHandler;
import org.example.Handlers.UserHandler;
import org.example.Http.Server;

public class Router {
    public static void route(Server server){

        //server.get("/users", UserController::createUser);
        server.handleValidRequests("/users", UserHandler::handleUser);
        server.handleValidRequests("/education", EducationHandler::handleEducation);
        server.handleValidRequests("/jobs", JobHandler::handleJob);
    }
}
