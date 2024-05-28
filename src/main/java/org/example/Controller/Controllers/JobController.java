package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.DAO.EducationDAO;
import org.example.Controller.DAO.JobDAO;
import org.example.Controller.DAO.UserDAO;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.JwtUtil;
import org.example.Controller.Parsers.OutPut;
import org.example.Model.Education;
import org.example.Model.Job;
import org.example.Model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobController extends Controller{
    private static final Gson gson = new Gson();


    public static String getJob (String email) throws SQLException {
        Job job = JobDAO.getJobByEmail(email);
        return job == null ? null : gson.toJson(job);
    }

    public static String getAllJobs () throws SQLException {
        ArrayList<Job> jobs = JobDAO.getAllJobs();
        return gson.toJson(jobs);
    }

    public static void createJob (String json) throws SQLException {
        Job job = gson.fromJson(json, Job.class);

        if (!UserDAO.doesUserExist(job.getEmail())) throw new SQLException("User does not exist");

        if (JobDAO.getJobByEmail(job.getEmail()) == null){
            JobDAO.saveJob(job);
        } else {
            JobDAO.updateJob(job);
        }
    }

    public static void deleteJob (String json) throws SQLException {
        Job job = gson.fromJson(json, Job.class);
        JobDAO.deleteJobByEmail(job.getEmail());
    }

    public static void deleteAllJobs () throws SQLException {
        JobDAO.deleteAllJobs();
    }
}
