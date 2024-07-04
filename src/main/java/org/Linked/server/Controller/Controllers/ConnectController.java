package org.Linked.server.Controller.Controllers;

import org.Linked.server.Model.Connect;
import org.Linked.server.Controller.DAO.ConnectDAO;
import org.Linked.server.Controller.DAO.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectController extends Controller {

    public static String getConnect(String senderEmail, String receiverEmail) throws SQLException {
        Connect connect = ConnectDAO.getConnectByEmail(senderEmail, receiverEmail);
        return gson.toJson(connect);
    }

    public static String getConnect(String email) throws SQLException {
        ArrayList<Connect> connects = ConnectDAO.getConnectsBySender(email);
        return gson.toJson(connects);
    }

    public static String getAllConnects() throws SQLException {
        ArrayList<Connect> connects = ConnectDAO.getAllConnects();
        return gson.toJson(connects);
    }

    public static void createConnect(String json) throws SQLException {
        Connect connect = gson.fromJson(json, Connect.class);

        if (UserDAO.getUserByEmail(connect.getSender()) == null) throw new SQLException("User does not exist");

        if (ConnectDAO.getConnectByEmail(connect.getSender(), connect.getReceiver()) == null) {
            ConnectDAO.saveConnect(connect);
        } else {
            ConnectDAO.updateConnect(connect);
        }
    }

    public static void deleteConnect(String senderEmail, String receiverEmail) throws SQLException {
        ConnectDAO.deleteConnectByEmail(senderEmail, receiverEmail);
    }

    public static void deleteAllConnects() throws SQLException {
        ConnectDAO.deleteAllConnects();
    }
}
