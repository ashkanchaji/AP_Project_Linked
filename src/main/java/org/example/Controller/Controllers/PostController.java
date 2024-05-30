package org.example.Controller.Controllers;

import org.example.Model.Post;

import java.sql.SQLException;
import java.util.ArrayList;

public class PostController extends Controller {
    public static String getPost(String email) throws SQLException {
        Post post = PostDAO.getPostByEmail(email);
        return post == null ? null : gson.toJson(post);
    }

    public static String getAllPosts() throws SQLException {
        ArrayList<Post> posts = PostDAO.getAllPosts();
        return gson.toJson(posts);
    }

    public static void createPost(String json) throws SQLException {
        Post post = gson.fromJson(json, Post.class);

        if (!UserDAO.doesUserExist(post.getUserId())) throw new SQLException("User does not exist");

        if (PostDAO.getPostByEmail(post.getUserId()) == null) {
            PostDAO.savePost(post);
        } else {
            PostDAO.updatePost(post);
        }
    }

    public static void deletePost(String json) throws SQLException {
        Post post = gson.fromJson(json, Post.class);
        PostDAO.deletePostByEmail(post.getUserId());
    }

    public static void deleteAllPosts() throws SQLException {
        PostDAO.deleteAllPosts();
    }
}
