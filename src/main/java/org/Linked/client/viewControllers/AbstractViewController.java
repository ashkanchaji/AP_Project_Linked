package org.Linked.client.viewControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.Linked.client.viewControllers.Utils.UserTypeAdapter;
import org.Linked.server.Model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AbstractViewController {
    private final static String PORT = "8080";
    protected final static String SERVER_ADDRESS = "http://127.0.0.1:" + PORT;

    protected double previousSceneWidth;
    protected double previousSceneHeight;

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserTypeAdapter())
            .create();
    protected static final Type USER_LIST_TYPE = new TypeToken<ArrayList<User>>() {}.getType();
}
