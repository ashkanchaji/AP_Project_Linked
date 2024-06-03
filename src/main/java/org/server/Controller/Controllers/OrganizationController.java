package org.server.Controller.Controllers;

import org.server.Controller.DAO.OrganizationDAO;
import org.server.Model.Job;
import org.server.Model.Organization;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrganizationController extends Controller {
    public static String getOrganization (String name) throws SQLException {
        Organization organization = OrganizationDAO.getOrganizationByName(name);
        return organization == null ? null : gson.toJson(organization);
    }

    public static String getAllOrganizations () throws SQLException {
        ArrayList<Organization> organizations = OrganizationDAO.getAllOrganizations();
        return gson.toJson(organizations);
    }

    public static void createOrganization (String json) throws SQLException {
        Organization organization = gson.fromJson(json, Organization.class);

//        if (UserDAO.getUserByEmail(job.getEmail()) == null) throw new SQLException("User does not exist");

        if (OrganizationDAO.getOrganizationByName(organization.getName()) == null){
            OrganizationDAO.saveOrganization(organization);
        } else {
            OrganizationDAO.updateOrganization(organization);
        }
    }

    public static void deleteJob (String name) throws SQLException {
        OrganizationDAO.deleteOrganizationByName(name);
    }

    public static void deleteAllJobs () throws SQLException {
        OrganizationDAO.deleteAllOrganizations();
    }


}
