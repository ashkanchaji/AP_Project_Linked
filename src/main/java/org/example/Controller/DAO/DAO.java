package org.example.Controller.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.DB.MySqlDB;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO {
    protected static final Connection connection = MySqlDB.fetchDB().getConnection();
    protected static final Gson gson = new Gson();
    protected static final Type arrayListType = new TypeToken<ArrayList<String>>() {}.getType();
}
