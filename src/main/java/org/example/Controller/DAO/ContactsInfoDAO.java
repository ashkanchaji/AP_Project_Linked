package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.CharacterNumberLimitException;
import org.example.Model.ContactsInfo;
import org.example.Model.Education;

import java.sql.*;
import java.util.ArrayList;

public class ContactsInfoDAO extends DAO{

    private static final String tableName = "contacts_info";
    private static final String tablePath = MySqlDB.getDBName() + "." + tableName;
    private static final String createContactsTableSQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45) , "
            + "link VARCHAR(40), "
            + "phoneNumber VARCHAR(40), "
            + "phoneType VARCHAR(40), "
            + "address VARCHAR(220), "                        //تاریخ تولد ، سیاست نمایش تاریخ تولد
            + "birthday DATE"
            + ")";

    public static ContactsInfo getContactsInfo (String email) throws SQLException {
        ContactsInfo contactsInfo = null;
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                contactsInfo = returnContactsInfo(resultSet);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return contactsInfo;
    }
    public static ArrayList<ContactsInfo> getAllContactsInformations () throws SQLException {
        ArrayList<ContactsInfo> contactsInformations = new ArrayList<>();
        String query = "SELECT * FROM " + tablePath;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                ContactsInfo contactsInfo = returnContactsInfo(resultSet);
                contactsInformations.add(contactsInfo);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return contactsInformations;
    }
    public static void saveContactsInfo (ContactsInfo contactsInfo) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "( email , link ,phoneNumber, phoneType, address, birthday, contactUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(createContactsTableSQL);
                }
            }
            executePreparedStatement(ps, contactsInfo);
        }
    }
    public static void updateContactsInfo(ContactsInfo contactsInfo) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET  email = ?,link = ? , phoneNumber = ?, phoneType = ?, address = ?, birthday = ?," +
                " contactUs = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(8, contactsInfo.getEmail());
            executePreparedStatement(ps, contactsInfo);
        }
    }
    public static void deleteContactsInfo(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }
    public static void deleteAllContactsInformation() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static ContactsInfo returnContactsInfo(ResultSet resultSet) throws SQLException, CharacterNumberLimitException {
        return new ContactsInfo(resultSet.getString("email"),
                resultSet.getString("link"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("phoneType"),
                resultSet.getString("address"),
                resultSet.getDate("birthday"),
                resultSet.getString("contactUS"));
    }

    private static void executePreparedStatement(PreparedStatement ps, ContactsInfo contactsInfo) throws SQLException {
        ps.setString(1, contactsInfo.getEmail());
        ps.setString(2, contactsInfo.getLink());
        ps.setString(3, contactsInfo.getPhoneNumber());
        ps.setString(4, contactsInfo.getPhoneType());
        ps.setString(5, contactsInfo.getAddress());
        ps.setDate(6, (Date) contactsInfo.getBirthday());
        ps.setString(7, contactsInfo.getContactUs());
        ps.executeUpdate();
    }
}
