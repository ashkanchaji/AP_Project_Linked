import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Model.User;
import org.junit.Test;

import java.sql.*;

import static org.example.Controller.Parsers.JwtUtil.generateToken;
import static org.example.Controller.Parsers.JwtUtil.parseToken;

public class Tests {
    @Test
    public void jwtTest() throws InvalidPassException, InvalidEmailException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = new User("achaji2563@gmail.com", "2", "3", "4sdhfl67d6f");

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
                    " VALUES ('achaji@gmail.com', 'ach256384', 'ashkan', 'chaji')");
            //statement.executeUpdate("DELETE FROM `projectlinked`.`users_info` WHERE first_name = 'ashkan'");
        }  catch (SQLIntegrityConstraintViolationException e){
            System.out.println("did it still add thow?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
