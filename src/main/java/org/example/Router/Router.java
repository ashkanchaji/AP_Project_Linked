package org.example.Router;

import org.example.Handlers.*;
import org.example.Http.Server;

public class Router {
    public static void route(Server server){

        server.handleValidRequests("/users", new UserHandler()::handle);
        server.handleValidRequests("/education", new EducationHandler()::handle);
        server.handleValidRequests("/jobs", new JobHandler()::handle);
        server.handleValidRequests("/contacts", new ContactsHandler()::handle);
        server.handleValidRequests("/posts", new PostHandler()::handle);
        server.handleValidRequests("/reposts", new RepostHandler()::handle);
        server.handleValidRequests("/comments", new ContactsHandler()::handle);

    }
}
