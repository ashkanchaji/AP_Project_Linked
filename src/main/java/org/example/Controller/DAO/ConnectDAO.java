package org.example.Controller.DAO;

import org.example.Model.Connect;
import org.example.Model.Job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectDAO extends GenericDAO<Connect> {
    private final String CREATE_CONNECTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "sender VARCHAR(45), "
            + "receiver VARCHAR(40), "
            + "note VARCHAR(500), "
            + ")";

    public ConnectDAO() {
        super("connect_info");
    }
    @Override
    protected Connect mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Connect(
                resultSet.getInt("type"),
                resultSet.getString("sender"),
                resultSet.getString("receiver"),
                resultSet.getString("notes")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_CONNECTS_TABLE_SQL;
    }

    public void saveConnect(Connect connect) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(sender , receiver , notes) " +
                "VALUES (?, ?, ?)";
        saveEntity(connect, query, (ps, j) -> {
            ps.setString(1, j.getSender());
            ps.setString(2, j.getReceiver());
            ps.setString(3, j.getNotes());
        });
    }

    public Connect getJobByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<Connect> getAllJobs() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateConnect(Connect connect) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET receiver = ?, notes = ? " +
                "WHERE sender = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, connect.getSender());
            ps.setString(2, connect.getReceiver());
            ps.setString(3, connect.getNotes());
            ps.executeUpdate();
        }
    }

    public void deleteConnectByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllConnects() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }


}
