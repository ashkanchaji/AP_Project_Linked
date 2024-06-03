package org.server.Controller.Controllers;

import org.server.Model.Job;
import org.server.Model.Like;

import java.sql.SQLException;
import java.util.ArrayList;

public class LikeController extends Controller {
    public static String getLike (String email) throws SQLException {
        Like like = LikeDAO.getLikeByEmail(email);
        return like == null ? null : gson.toJson(like);
    }

    public static String getAllLikes () throws SQLException {
        ArrayList<Like> likes = LikeDAO.getAllLikes();
        return gson.toJson(likes);
    }

    public static void createLike (String json) throws SQLException {
        Like like = gson.fromJson(json, Like.class);

        if (UserDAO.getUserByEmail(like.getLiker()) == null) throw new SQLException("User does not exist");

        if (LikeDAO.getLikeByEmail(like.getLiker()) == null){
            LikeDAO.saveLike(like);
        } else {
            LikeDAO.updateLike(like);
        }
    }

    public static void deleteLike (String json) throws SQLException {  //logic should change
        Like like = gson.fromJson(json , Like.class);
        LikeDAO.deleteLikeByEmail(like.getLiker());
    }

    public static void deleteAllLikes () throws SQLException {
        LikeDAO.deleteAllLikes();
    }



}
