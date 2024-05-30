package org.example.Controller.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.DB.MySqlDB;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

public abstract class GenericDAO<T> {
    protected static final Connection connection = MySqlDB.fetchDB().getConnection();
    protected static final Gson gson = new Gson();
    protected static final Type arrayListType = new TypeToken<ArrayList<String>>() {}.getType();
    protected final String tableName;
    protected final String tablePath;

    protected GenericDAO(String tableName) {
        this.tableName = tableName;
        this.tablePath = MySqlDB.getDBName() + "." + tableName;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    public void saveEntity(T entity, String insertSQL, StatementPreparer<T> preparer) throws SQLException {
        checkTableExistence();

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            preparer.prepare(ps, entity);
            ps.executeUpdate();
        }
    }

    public T getEntity(String query, String email) throws SQLException {
        checkTableExistence();

        T entity = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        }
        return entity;
    }

    public ArrayList<T> getAllEntities(String query) throws SQLException {
        checkTableExistence();

        ArrayList<T> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        }
        return entities;
    }

    public void deleteEntity(String query, String email) throws SQLException {
        checkTableExistence();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    public void deleteAllEntities(String query) throws SQLException {
        checkTableExistence();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    protected abstract String getCreateTableSQL();

    @FunctionalInterface
    public interface StatementPreparer<T> {
        void prepare(PreparedStatement ps, T entity) throws SQLException;
    }

    protected void checkTableExistence() throws SQLException{
        if (!MySqlDB.doesTableExist(connection, tableName)) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(getCreateTableSQL());
            }
        }
    }
}
