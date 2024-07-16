package org.Linked.server.Controller.DB;

import java.sql.*;

public class MySqlDB {
    private static final String DB_Name = "projectlinked";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "projectLinked";
    private static MySqlDB dataBase = null;
    private static Connection connection;

    private MySqlDB() {
        createConnection();
    }

    private static void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_Name, USER_NAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MySqlDB fetchDB() {
        if (dataBase == null){
            dataBase = new MySqlDB();
        }
        return dataBase;
    }

    public Connection getConnection() {
        return connection;
    }

    public static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData dbMetaData = connection.getMetaData();
        try (ResultSet rs = dbMetaData.getTables(null, null, tableName, new String[] {"TABLE"})) {
            return rs.next();
        }
    }

    public static String getDB_Name(){
        return DB_Name;
    }
}
