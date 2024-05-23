package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;

import java.sql.Statement;

public class ContactsInfoDAO extends DAO{
    private static final String tableName = "contacts_info";
    private static final String tablePath = MySqlDB.getDBName() + "." + tableName;
    private static final String createContactsTableSQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45) , "
            + "link VARCHAR(40), "
            + "phone_number VARCHAR(40), "
            + "address VARCHAR(220), "
            + "birth_day DATE, "
            + "contact_us VARCHAR(40)"
            + ")";

}
