package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.ContactsInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactsInfoDAO extends GenericDAO<ContactsInfo> {
    private final String CREATE_CONTACTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "contactEmail VARCHAR(40), "
            + "phoneNumber VARCHAR(40), "
            + "phoneType VARCHAR(40), "
            + "address VARCHAR(220), "
            + "birthday DATE, "
            + "contactUS VARCHAR(1000)"
            + ")";

    public ContactsInfoDAO() {
        super("contacts_info");
    }

    @Override
    protected ContactsInfo mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new ContactsInfo(
                resultSet.getString("email"),
                resultSet.getString("contactEmail"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("phoneType"),
                resultSet.getString("address"),
                resultSet.getDate("birthday"),
                resultSet.getString("contactUS")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_CONTACTS_TABLE_SQL;
    }

    public void saveContactsInfo(ContactsInfo contactsInfo) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, contactEmail, phoneNumber, phoneType, address, birthday, contactUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        saveEntity(contactsInfo, query, (ps, c) -> {
            ps.setString(1, c.getEmail());
            ps.setString(2, c.getContactEmail());
            ps.setString(3, c.getPhoneNumber());
            ps.setString(4, c.getPhoneType());
            ps.setString(5, c.getAddress());
            ps.setDate(6, c.getBirthday());
            ps.setString(7, c.getContactUs());
        });
    }

    public ContactsInfo getContactsInfoByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<ContactsInfo> getAllContactsInfo() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateContactsInfo(ContactsInfo contactsInfo) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET contactEmail = ?, phoneNumber = ?, phoneType = ?, address = ?, birthday = ?, contactUS = ?" +
                " WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, contactsInfo.getContactEmail());
            ps.setString(2, contactsInfo.getPhoneNumber());
            ps.setString(3, contactsInfo.getPhoneType());
            ps.setString(4, contactsInfo.getAddress());
            ps.setDate(5, contactsInfo.getBirthday());
            ps.setString(6, contactsInfo.getContactUs());
            ps.setString(7, contactsInfo.getEmail());
            ps.executeUpdate();
        }
    }

    public void deleteContactsInfoByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllContactsInfo() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
