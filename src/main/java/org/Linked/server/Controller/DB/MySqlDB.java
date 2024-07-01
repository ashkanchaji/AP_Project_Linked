package org.Linked.server.Controller.DB;

import java.sql.*;

// maybe database shouldn't be singleton only if we need to change the schema...
// if we only need to word with new 'table' s then the singleton is fine.
public class MySqlDB {
    private static final String DBName = "projectlinked";
    private static final String userName = "root";
    private static final String password = "projectlinked";
    private static MySqlDB dataBase = null;
    private static Connection connection;

    private MySqlDB() {
        createConnection();
    }

    private static void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName,
                    userName, password);

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

    public static String getDBName(){
        return DBName;
    }
}
