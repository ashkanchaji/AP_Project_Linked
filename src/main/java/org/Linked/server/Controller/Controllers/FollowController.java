package org.Linked.server.Controller.Controllers;

import org.Linked.server.Model.Follow;

import java.sql.SQLException;
import java.util.ArrayList;

public class FollowController extends  Controller{
    public static String getFollow (String followerEmail, String followingEmail) throws SQLException{
        Follow follow = FollowDAO.getFollowByEmail(followerEmail, followingEmail);
        return gson.toJson(follow);
    }

    public static String getFollow (String email) throws SQLException {
        ArrayList<Follow> follows = FollowDAO.getFollowsByFollower(email);
        return gson.toJson(follows);
    }

    public static String getAllFollows () throws SQLException {
        ArrayList<Follow> follows = FollowDAO.getAllFollows();
        return gson.toJson(follows);
    }

    public static void createFollow (String json) throws SQLException {
        Follow follow = gson.fromJson(json, Follow.class);

        if (UserDAO.getUserByEmail(follow.getFollower()) == null) throw new SQLException("User does not exist");

        if (FollowDAO.getFollowByEmail(follow.getFollower(), follow.getFollowing()) == null){
            FollowDAO.saveFollow(follow);
        }
//        else {
//            FollowDAO.updateFollow(follow);
//        }
    }

    public static void deleteFollow (String followerEmail, String followingEmail) throws SQLException {
        FollowDAO.deleteFollowByEmail(followerEmail, followingEmail);
    }

    public static void deleteAllFollows () throws SQLException {
        FollowDAO.deleteAllFollows();
    }


}
