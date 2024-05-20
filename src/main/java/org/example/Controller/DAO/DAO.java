package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;

import java.sql.Connection;

public abstract class DAO {
    protected static final Connection connection = MySqlDB.fetchDB().getConnection();
}
