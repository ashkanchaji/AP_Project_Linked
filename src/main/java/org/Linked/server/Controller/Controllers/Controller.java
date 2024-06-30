package org.Linked.server.Controller.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.Linked.client.viewControllers.Utils.UserTypeAdapter;
import org.Linked.server.Controller.DAO.*;
import org.Linked.server.Controller.DAO.*;
import org.Linked.server.Controller.DAO.*;
import org.Linked.server.Controller.Util.FollowTypeAdapter;
import org.Linked.server.Model.Follow;
import org.Linked.server.Model.User;

public abstract class Controller {
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserTypeAdapter())
            .registerTypeAdapter(Follow.class, new FollowTypeAdapter())
            .create();
    protected static final org.Linked.server.Controller.DAO.UserDAO UserDAO = new UserDAO();
    protected static final JobDAO JobDAO = new JobDAO();
    protected static final EducationDAO EducationDAO = new EducationDAO();
    protected static final ContactsInfoDAO ContactsInfoDAO = new ContactsInfoDAO();
    protected static final PostDAO PostDAO = new PostDAO();
    protected static final CommentDAO CommentDAO = new CommentDAO();
    protected static final RepostDAO RepostDAO = new RepostDAO();
    protected static final org.Linked.server.Controller.DAO.OrganizationDAO OrganizationDAO = new OrganizationDAO();
    protected static final org.Linked.server.Controller.DAO.LikeDAO LikeDAO = new LikeDAO();
    protected static final org.Linked.server.Controller.DAO.FollowDAO FollowDAO = new FollowDAO();
    protected static final org.Linked.server.Controller.DAO.ConnectDAO ConnectDAO = new ConnectDAO();




}
