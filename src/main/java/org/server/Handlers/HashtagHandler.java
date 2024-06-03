package org.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.server.Controller.Controllers.PostController;

import java.io.IOException;
import java.sql.SQLException;

public class HashtagHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        if (method.equals("GET")) {
            if (splitPath.length >= 3) {
                String hashtag = splitPath[splitPath.length - 1];
                String postsJson = PostController.getPostsByHashtag("#" + hashtag);
                response = postsJson == null ? "No posts found with this hashtag!" : postsJson;
                return response;
            }
        }
        response = "Unsupported method or path";

        return response;
    }
}
