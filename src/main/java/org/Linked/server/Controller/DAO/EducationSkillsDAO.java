package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.EducationSkills;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationSkillsDAO extends GenericDAO<EducationSkills> {
    private final String CREATE_EDUCATION_SKILLS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "skill1 VARCHAR(100), "
            + "skill2 VARCHAR(100), "
            + "skill3 VARCHAR(100), "
            + "skill4 VARCHAR(100), "
            + "skill5 VARCHAR(100)"
            + ")";

    public EducationSkillsDAO() {
        super("education_skills");
    }

    @Override
    protected EducationSkills mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new EducationSkills(
                resultSet.getString("email"),
                resultSet.getString("skill1"),
                resultSet.getString("skill2"),
                resultSet.getString("skill3"),
                resultSet.getString("skill4"),
                resultSet.getString("skill5")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_EDUCATION_SKILLS_TABLE_SQL;
    }

    public void saveEducationSkills(EducationSkills educationSkills) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, skill1, skill2, skill3, skill4, skill5) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        saveEntity(educationSkills, query, (ps, e) -> {
            ps.setString(1, e.getEmail());
            ps.setString(2, e.getSkill1());
            ps.setString(3, e.getSkill2());
            ps.setString(4, e.getSkill3());
            ps.setString(5, e.getSkill4());
            ps.setString(6, e.getSkill5());
        });
    }

    public EducationSkills getEducationSkillsByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<EducationSkills> getAllEducationSkills() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateEducationSkills(EducationSkills educationSkills) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET skill1 = ?, skill2 = ?, skill3 = ?, skill4 = ?, skill5 = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, educationSkills.getSkill1());
            ps.setString(2, educationSkills.getSkill2());
            ps.setString(3, educationSkills.getSkill3());
            ps.setString(4, educationSkills.getSkill4());
            ps.setString(5, educationSkills.getSkill5());
            ps.setString(6, educationSkills.getEmail());
            ps.executeUpdate();
        }
    }

    public void deleteEducationSkillsByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllEducationSkills() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
