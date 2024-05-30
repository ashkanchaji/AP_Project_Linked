package org.example.Controller.DAO;
import org.example.Model.Job;
import org.example.Model.Organization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class OrganizationDAO extends  GenericDAO<Organization> {
    private final String CREATE_ORGANIZATIONS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(45), "
            + "rank VARCHAR(45), "
            + "enterYear DATE, "
            + "exitYear DATE, "
            + ")";

    public OrganizationDAO() {
        super("organization_info");
    }
    @Override
    protected Organization mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Organization(
                resultSet.getString("name"),
                resultSet.getString("rank"),
                resultSet.getDate("enterYear"),
                resultSet.getDate("exitYear")

        );
    }
    @Override
    protected String getCreateTableSQL() {
        return CREATE_ORGANIZATIONS_TABLE_SQL;
    }

    public void saveJob(Organization organization) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(name , rank , enterYear , exitYear) " +
                "VALUES (?, ?, ?, ?)";
        saveEntity(organization, query, (ps, j) -> {
            ps.setString(1, j.getName());
            ps.setString(2, j.getRank());
            ps.setDate(3, j.getEnterYear());
            ps.setDate(4, j.getExitYear());
        });
    }

    public Organization getOrganizationByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<Organization> getAllOrganizations() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateJob(Organization organization) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET rank = ?, enterYear = ?, exitYear = ? " +
                "WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, organization.getName());
            ps.setString(2, organization.getRank());
            ps.setDate(3, organization.getEnterYear());
            ps.setDate(4, organization.getExitYear());
            ps.executeUpdate();
        }
    }

    public void deleteOrganizationByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllOrganizations() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }

}
