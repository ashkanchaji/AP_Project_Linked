package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Organization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrganizationDAO extends GenericDAO<Organization> {

    private final String CREATE_ORGANIZATIONS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "name VARCHAR(45), "
            + "rank VARCHAR(45), "
            + "enterYear DATE, "
            + "exitYear DATE"
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

    public void saveOrganization(Organization organization) throws SQLException { // Corrected method name
        String query = "INSERT INTO " + tablePath +
                "(name, rank, enterYear, exitYear) " +
                "VALUES (?, ?, ?, ?)";
        saveEntity(organization, query, (ps, j) -> {
            ps.setString(1, j.getName());
            ps.setString(2, j.getRank());
            ps.setDate(3, j.getEnterYear());
            ps.setDate(4, j.getExitYear());
        });
    }

    public Organization getOrganizationByName(String name) throws SQLException { // Corrected method name and parameter
        String query = "SELECT * FROM " + tablePath + " WHERE name = ?";
        return getEntity(query, name);
    }

    public ArrayList<Organization> getAllOrganizations() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateOrganization(Organization organization) throws SQLException { // Corrected method name
        String query = "UPDATE " + tablePath +
                " SET rank = ?, enterYear = ?, exitYear = ? " +
                "WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, organization.getRank()); // Corrected parameter order
            ps.setDate(2, organization.getEnterYear());
            ps.setDate(3, organization.getExitYear());
            ps.setString(4, organization.getName());
            ps.executeUpdate();
        }
    }

    public void deleteOrganizationByName(String name) throws SQLException { // Corrected method name and parameter
        String query = "DELETE FROM " + tablePath + " WHERE name = ?";
        deleteEntity(query, name);
    }

    public void deleteAllOrganizations() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
