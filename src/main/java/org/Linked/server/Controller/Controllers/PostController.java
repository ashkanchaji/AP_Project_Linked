package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.DAO.AbstractPostDAO;
import org.Linked.server.Model.Post;
import org.Linked.server.Model.Comment;
import org.Linked.server.Model.Repost;

import java.sql.SQLException;
import java.util.ArrayList;

public class PostController extends Controller {

    // Post-related methods
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

        if (UserDAO.getUserByEmail(post.getUserId()) == null) throw new SQLException("User does not exist");

        if (PostDAO.getPostByEmail(post.getUserId()) == null) {
            PostDAO.savePost(post);
        } else {
            PostDAO.updatePost(post);
        }
    }

    public static void deletePost(String json) throws SQLException {
        Post post = gson.fromJson(json, Post.class);
        PostDAO.deletePostByEmail(post.getUserId(), post.getCreatedAt());
    }

    public static void deleteAllPosts() throws SQLException {
        PostDAO.deleteAllPosts();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Comment-related methods
    public static String getComment(String email) throws SQLException {
        Comment comment = CommentDAO.getCommentByEmail(email);
        return comment == null ? null : gson.toJson(comment);
    }

    public static String getAllComments() throws SQLException {
        ArrayList<Comment> comments = CommentDAO.getAllComments();
        return gson.toJson(comments);
    }

    public static void createComment(String json) throws SQLException {
        Comment comment = gson.fromJson(json, Comment.class);

        if (UserDAO.getUserByEmail(comment.getUserId()) == null) throw new SQLException("User does not exist");

        if (CommentDAO.getCommentByEmail(comment.getUserId()) == null) {
            CommentDAO.saveComment(comment);
        } else {
            CommentDAO.updateComment(comment);
        }
    }

    public static void deleteComment(String json) throws SQLException {
        Comment comment = gson.fromJson(json, Comment.class);
        CommentDAO.deleteCommentByEmail(comment.getUserId(), comment.getCreatedAt());
    }

    public static void deleteAllComments() throws SQLException {
        CommentDAO.deleteAllComments();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Repost-related methods
    public static String getRepost(String email) throws SQLException {
        Repost repost = RepostDAO.getRepostByEmail(email);
        return repost == null ? null : gson.toJson(repost);
    }

    public static String getAllReposts() throws SQLException {
        ArrayList<Repost> reposts = RepostDAO.getAllReposts();
        return gson.toJson(reposts);
    }

    public static void createRepost(String json) throws SQLException {
        Repost repost = gson.fromJson(json, Repost.class);

        if (UserDAO.getUserByEmail(repost.getUserId()) == null) throw new SQLException("User does not exist");

        if (RepostDAO.getRepostByEmail(repost.getUserId()) == null) {
            RepostDAO.saveRepost(repost);
        } else {
            RepostDAO.updateRepost(repost);
        }
    }

    public static void deleteRepost(String json) throws SQLException {
        Repost repost = gson.fromJson(json, Repost.class);
        RepostDAO.deleteRepostByEmail(repost.getUserId(), repost.getCreatedAt());
    }

    public static void deleteAllReposts() throws SQLException {
        RepostDAO.deleteAllReposts();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getPostsByHashtag (String hashtag) throws SQLException {
        ArrayList<Post> posts =  AbstractPostDAO.getPostsByHashTag(hashtag);
        return gson.toJson(posts);
    }
}
