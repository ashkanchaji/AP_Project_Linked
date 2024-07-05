package org.Linked.server.Controller.DAO;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.DirectMessage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirectMessageDAO extends GenericDAO<DirectMessage> {
    private final String CREATE_DM_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "dmId VARCHAR(36) NOT NULL, "
            + "posterID VARCHAR(45), "
            + "receiverID VARCHAR(45), "
            + "text VARCHAR(1900), "
            + "createdAt DATE"
            + ")";

    private final String INSERT_DM_SQL = "INSERT INTO " + tablePath +
            "(dmId, posterID, receiverID, text, createdAt) " +
            "VALUES (?, ?, ?, ?, ?)";

    private final String UPDATE_DM_SQL = "UPDATE " + tablePath + " SET " +
            "text = ?, createdAt = ? " +
            "WHERE dmId = ?";

    public DirectMessageDAO() {
        super("direct_messages");
    }

    @Override
    protected DirectMessage mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        try {
            return new DirectMessage(
                    resultSet.getString("dmId"),
                    resultSet.getString("posterID"),
                    resultSet.getString("receiverID"),
                    resultSet.getString("text"),
                    resultSet.getDate("createdAt")
            );
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_DM_TABLE_SQL;
    }

    public void saveDirectMessage(DirectMessage dm) throws SQLException {
        saveEntity(dm, INSERT_DM_SQL, (ps, d) -> {
            ps.setString(1, d.getDmId());
            ps.setString(2, d.getPosterID());
            ps.setString(3, d.getReceiverID());
            ps.setString(4, d.getText());
            ps.setDate(5, d.getCreatedAt());
        });
    }

    public DirectMessage getDirectMessageById(String dmId) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE dmId = ?";
        return getEntity(query, dmId);
    }

    public ArrayList<DirectMessage> getAllDirectMessages() throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

//    public void updateDirectMessage(DirectMessage dm) throws SQLException {
//        checkTableExistence();
//        updatePost(dm, UPDATE_DM_SQL);
//    }

    public void deleteDirectMessageById(String dmId) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE dmId = ?";
        deleteEntity(query, dmId);
    }

    public void deleteAllDirectMessages() throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
