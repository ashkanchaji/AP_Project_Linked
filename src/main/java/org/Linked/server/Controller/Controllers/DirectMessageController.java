package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.DAO.DirectMessageDAO;
import org.Linked.server.Controller.DAO.UserDAO;
import org.Linked.server.Model.DirectMessage;

import java.sql.SQLException;
import java.util.ArrayList;

public class DirectMessageController extends Controller {
    private static final DirectMessageDAO dmDAO = new DirectMessageDAO();

    public static String getDirectMessage(String dmId) throws SQLException {
        DirectMessage dm = dmDAO.getDirectMessageById(dmId);
        return dm == null ? null : gson.toJson(dm);
    }

    public static String getAllDirectMessages() throws SQLException {
        ArrayList<DirectMessage> dms = dmDAO.getAllDirectMessages();
        return gson.toJson(dms);
    }

    public static void createDirectMessage(String json) throws SQLException {
        DirectMessage dm = gson.fromJson(json, DirectMessage.class);

        if (UserDAO.getUserByEmail(dm.getPosterID()) == null || UserDAO.getUserByEmail(dm.getReceiverID()) == null) {
            throw new SQLException("User does not exist");
        }

        if (dmDAO.getDirectMessageById(dm.getDmId()) == null) {
            dmDAO.saveDirectMessage(dm);
        }
    }

    public static void deleteDirectMessageById(String dmId) throws SQLException {
        dmDAO.deleteDirectMessageById(dmId);
    }

    public static void deleteAllDirectMessages() throws SQLException {
        dmDAO.deleteAllDirectMessages();
    }
}
