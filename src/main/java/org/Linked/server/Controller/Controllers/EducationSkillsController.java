package org.Linked.server.Controller.Controllers;

import org.Linked.server.Model.EducationSkills;

import java.sql.SQLException;
import java.util.ArrayList;

public class EducationSkillsController extends Controller{

    public static String getEducationSkills(String email) throws SQLException {
        EducationSkills educationSkills = EducationSkillsDAO.getEducationSkillsByEmail(email);
        return educationSkills == null ? null : gson.toJson(educationSkills);
    }

    public static String getAllEducationSkills() throws SQLException {
        ArrayList<EducationSkills> educationSkillsList = EducationSkillsDAO.getAllEducationSkills();
        return gson.toJson(educationSkillsList);
    }

    public static void createEducationSkills(String json) throws SQLException {
        EducationSkills educationSkills = gson.fromJson(json, EducationSkills.class);

        if (UserDAO.getUserByEmail(educationSkills.getEmail()) == null) throw new SQLException("User does not exist");

        if (EducationSkillsDAO.getEducationSkillsByEmail(educationSkills.getEmail()) == null) {
            EducationSkillsDAO.saveEducationSkills(educationSkills);
        } else {
            EducationSkillsDAO.updateEducationSkills(educationSkills);
        }
    }

    public static void deleteEducationSkills(String email) throws SQLException {
        EducationSkillsDAO.deleteEducationSkillsByEmail(email);
    }

    public static void deleteAllEducationSkills() throws SQLException {
        EducationSkillsDAO.deleteAllEducationSkills();
    }
}
