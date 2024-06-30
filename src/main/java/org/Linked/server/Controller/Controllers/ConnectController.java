package org.Linked.server.Controller.Controllers;

import org.Linked.server.Model.Connect;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectController extends  Controller{
    public static String getConnect (String email) throws SQLException {
        Connect connect = ConnectDAO.getConnectByEmail(email);
        return connect == null ? null : gson.toJson(connect);
    }

    public static String getAllConnects () throws SQLException {
        ArrayList<Connect> connects = ConnectDAO.getAllConnects();
        return gson.toJson(connects);
    }

    public static void createConnect (String json) throws SQLException {
        Connect connect = gson.fromJson(json, Connect.class);

        if (UserDAO.getUserByEmail(connect.getSender()) == null) throw new SQLException("User does not exist");

        if (ConnectDAO.getConnectByEmail(connect.getSender()) == null){
            ConnectDAO.saveConnect(connect);
        }
//        else {
//            ConnectDAO.updateConnect(connect);
//        }
    }

    public static void deleteConnect (String email) throws SQLException {
        ConnectDAO.deleteConnectByEmail(email);
    }

    public static void deleteAllConnect () throws SQLException {
        ConnectDAO.deleteAllConnects();
    }
}
