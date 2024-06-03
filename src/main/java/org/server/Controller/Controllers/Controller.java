package org.server.Controller.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.server.Controller.DAO.*;

public abstract class Controller {
    protected static final Gson gson = new  GsonBuilder().setPrettyPrinting().create();
    protected static final UserDAO UserDAO = new UserDAO();
    protected static final JobDAO JobDAO = new JobDAO();
    protected static final EducationDAO EducationDAO = new EducationDAO();
    protected static final ContactsInfoDAO ContactsInfoDAO = new ContactsInfoDAO();
    protected static final PostDAO PostDAO = new PostDAO();
    protected static final CommentDAO CommentDAO = new CommentDAO();
    protected static final RepostDAO RepostDAO = new RepostDAO();
    protected static final OrganizationDAO OrganizationDAO = new OrganizationDAO();

}
