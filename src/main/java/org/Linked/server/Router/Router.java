package org.Linked.server.Router;

import org.Linked.server.Handlers.*;
import org.Linked.server.Handlers.*;
import org.Linked.server.Handlers.*;
import org.Linked.server.Http.Server;

public class Router {
    public static void route(Server server){

        server.handleValidRequests("/login", new LoginHandler()::handle);
        server.handleValidRequests("/users", new UserHandler()::handle);
        server.handleValidRequests("/education", new EducationHandler()::handle);
        server.handleValidRequests("/educationSkills", new EducationSkillsHandler()::handle);
        server.handleValidRequests("/jobs", new JobHandler()::handle);
        server.handleValidRequests("/contacts", new ContactsHandler()::handle);
        server.handleValidRequests("/posts", new PostHandler()::handle);
        server.handleValidRequests("/follow", new FollowHandler()::handle);
        server.handleValidRequests("/connect", new ConnectHandler()::handle);
        server.handleValidRequests("/reposts", new RepostHandler()::handle);
        server.handleValidRequests("/comments", new ContactsHandler()::handle);
        server.handleValidRequests("/hashtags", new HashtagHandler()::handle);
    }
}
