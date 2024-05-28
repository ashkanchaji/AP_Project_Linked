package org.example.Controller.Controllers;

import org.example.Controller.DAO.*;

public abstract class Controller {
    protected static final UserDAO UserDAO = new UserDAO();
    protected static final JobDAO JobDAO = new JobDAO();
    protected static final EducationDAO EducationDAO = new EducationDAO();
    protected static final ContactsInfoDAO ContactsInfoDAO = new ContactsInfoDAO();
}
