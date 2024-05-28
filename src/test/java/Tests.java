import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import org.example.Controller.Controllers.UserController;
import org.example.Controller.DAO.UserDAO;
import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Model.Education;
import org.example.Model.Job;
import org.example.Model.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import static org.example.Controller.Parsers.JwtUtil.generateToken;
import static org.example.Controller.Parsers.JwtUtil.parseToken;

public class Tests {
    @Test
    public void jwtTest() throws InvalidPassException, InvalidEmailException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = new User("achaji25563@gmail.com", "2", "3", "4sdhfl67d6f");

        String jwt = generateToken(gson.toJson(user));
        System.out.println("Generated JWT: " + jwt);

        Claims claims = parseToken(jwt);
        if (claims != null) {
            System.out.println("Subject: " + claims.getSubject());

            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
        }
    }

    @Test
    public void testDB(){
        MySqlDB mySqlDB = MySqlDB.fetchDB();

        Connection connection = mySqlDB.getConnection();

        // java.sql.SQLIntegrityConstraintViolationException
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO `projectlinked`.`users_info` (`email`, `password`, `first_name`, `last_name`)" +
                    " VALUES ('achajii@gmail.com', 'ach256384', 'ashkan', 'chaji')");
            //statement.executeUpdate("DELETE FROM `projectlinked`.`users_info` WHERE first_name = 'ashkan'");
        }  catch (SQLIntegrityConstraintViolationException e){
            System.out.println("did it still add thow?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateTable(){
        Gson gson = new Gson();

//        try {
//            String response = UserController.getUsers();
//
//            System.out.println(response);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        Education job = new Education("achaji2563@gmail.com", "dfh", "khsd", null, null, null, null, null, null);
        System.out.println(gson.toJson(job));
    }

//    @Test
//    @DisplayName("---- adding a user")
//    public void testcase1() {
//        try {
//            URL url = new URL("http://localhost:8080/users");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "json"); // not needed
//
//            con.setDoInput(true); // enables input stream, no need
//            con.setDoOutput(true); // enables output stream
//            JsonHandler.sendJsonObject(con.getOutputStream(), new User("Ali", "a1234").toJSON());
//
//            if (con.getResponseCode() / 100 == 2) {
//                System.out.println("test1 result: " + JsonHandler.getJsonObject(con.getInputStream()));
//            } else {
//                System.out.println("Server returned HTTP code " + con.getResponseCode());
//            }
//            con.disconnect();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new AssertionError(e);
//        }
//    }
}
