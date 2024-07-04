package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectDAO extends GenericDAO<Connect> {
    private final String CREATE_CONNECTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "sender VARCHAR(45), "
            + "receiver VARCHAR(45), "
            + "notes VARCHAR(500), "
            + "pending BOOLEAN"
            + ")";

    public ConnectDAO() {
        super("connect_info");
    }

    @Override
    protected Connect mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Connect(
                resultSet.getString("sender"),
                resultSet.getString("receiver"),
                resultSet.getString("notes"),
                resultSet.getBoolean("pending")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_CONNECTS_TABLE_SQL;
    }

    public void saveConnect(Connect connect) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(sender, receiver, notes, pending) " +
                "VALUES (?, ?, ?, ?)";
        saveEntity(connect, query, (ps, j) -> {
            ps.setString(1, j.getSender());
            ps.setString(2, j.getReceiver());
            ps.setString(3, j.getNotes());
            ps.setBoolean(4, j.isPending());
        });
    }

    public Connect getConnectByEmail(String senderEmail, String receiverEmail) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE sender = ? AND receiver = ?";
        checkTableExistence();

        Connect entity = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, senderEmail);
            statement.setString(2, receiverEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        }
        return entity;
    }

    public ArrayList<Connect> getAllConnects() throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateConnect(Connect connect) throws SQLException {
        checkTableExistence();
        String query = "UPDATE " + tablePath +
                " SET receiver = ?, notes = ?, pending = ? " +
                "WHERE sender = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, connect.getReceiver());
            ps.setString(2, connect.getNotes());
            ps.setBoolean(3, connect.isPending());
            ps.setString(4, connect.getSender());
            ps.executeUpdate();
        }
    }

    public void deleteConnectByEmail(String senderEmail, String receiverEmail) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE sender = ? AND receiver = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, senderEmail);
            statement.setString(2, receiverEmail);
            statement.executeUpdate();
        }
    }

    public void deleteAllConnects() throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }

    public ArrayList<Connect> getConnectsBySender(String sender) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE sender = ? OR receiver = ?";
        ArrayList<Connect> connects = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sender);
            statement.setString(2, sender);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                connects.add(mapResultSetToEntity(resultSet));
            }
        }
        return connects;
    }

    private ArrayList<Connect> getEntities(String query, String parameter) throws SQLException {
        checkTableExistence();

        ArrayList<Connect> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        }
        return entities;
    }
}
