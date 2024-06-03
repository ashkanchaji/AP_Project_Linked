package org.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.server.Controller.Controllers.JobController;
import org.server.Controller.Controllers.OrganizationController;
import org.server.Model.Organization;

import java.io.IOException;
import java.sql.SQLException;

public class OrganizationHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String organizationJson = OrganizationController.getOrganization(email);
                    response = organizationJson == null ? "No such job info found!" : organizationJson;
                } else {
                    response = OrganizationController.getAllOrganizations();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    OrganizationController.deleteOrganization(email);
                } else {
                    OrganizationController.deleteAllOrganizations();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String organizationJson = readRequestBody(exchange);
                OrganizationController.createOrganization(organizationJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }

}
