# Linked: A Linkedin-like Java Application made using JavaFX

## Description

Project Linked is an application built using Java, JavaFX and some CSS. The application follows an MVC (Model-View-Controller) architecture and interacts with a MySQL database through RESTful API endpoints. The server handles incoming requests, delegates them to the appropriate controllers, which in turn interact with the data access layer to process and retrieve data from the database.
This was the final project for the Advances Programming course at Amir Kabir University of Technology(@AUT-CE). 

## Table of Contents

- [Installation](#installation)
- [Features](#features)
- [Endpoints](#endpoints)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [License](#license)
- [Contributing](#contributing)

## Installation

To run the application, follow these steps:

1. Clone the repository to your local machine.

   ```bash
   git clone git@github.com:ashkanchaji/AP_Project_Linked.git
   ```

2. Set up your MySQL database and configure the connection details in the application. Open the `MySqlDB.java` file located at `src/main/java/org/Linked/server/Controller/DB/MySqlDB.java`.

   ```java
   package org.Linked.server.Controller.DB;

   import java.sql.*;

   public class MySqlDB {
       private static final String DB_Name = "projectlinked";
       private static final String USER_NAME = "root";
       private static final String PASSWORD = "projectLinked";
       private static MySqlDB dataBase = null;
       private static Connection connection;
    
       private MySqlDB() {
           createConnection();
       }
    
       private static void createConnection(){
           try {
               Class.forName("com.mysql.cj.jdbc.Driver");
    
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_Name, USER_NAME, PASSWORD);
    
           } catch (ClassNotFoundException | SQLException e) {
               throw new RuntimeException(e);
           }
       }
    
       public static MySqlDB fetchDB() {
           if (dataBase == null){
               dataBase = new MySqlDB();
           }
           return dataBase;
       }
    
       public Connection getConnection() {
           return connection;
       }
    
       public static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
           DatabaseMetaData dbMetaData = connection.getMetaData();
           try (ResultSet rs = dbMetaData.getTables(null, null, tableName, new String[] {"TABLE"})) {
               return rs.next();
           }
       }
    
       public static String getDBName(){
           return DB_Name;
       }
   }
   ```

3. In the `MySqlDB.java` file, modify the following lines to match your MySQL database configuration:

   ```java
   private static final String DB_Name = "your_database_name";
   private static final String USER_NAME = "your_username";
   private static final String PASSWORD = "your_password";

   connection = DriverManager.getConnection("jdbc:mysql://your_mysql_host:your_port/" + DB_Name, USER_NAME, PASSWORD);
   ```

4. Build and run the server component.

5. Build and run the client application.

Make sure to replace the placeholders (e.g., `your_username`, `your_database_name`, etc.) with the actual values relevant to your MySQL database setup.

After following these steps, users will be able to set up the database connection correctly and run your application smoothly.

## Features

1. User Management
   - Create, retrieve and update users.
   - Add bios and profile images.
   - Follow, unfollow and connect other users.

2. Post Management
   - Create posts.
   - Like and comment.
   - View customized homePage.
   
3. User Profile
   - View user profiles.
   - Edit profile details.
   - Add Education, Skills and more.

4. Messaging, Notifications and Searching
   - View and send messages.
   - View notifications.
   - Search users and hashtags.

## Endpoints

Below are the available API endpoints in the server:

```java
// User Endpoints
GET /api/users
GET /api/users/:username
POST /api/users
PUT /api/users
DELETE /api/users/:username
DELETE /api/users

// Education Endpoints
GET /api/education
GET /api/education/:username
POST /api/education/:username
PUT /api/education/:username
DELETE /api/education/:username
DELETE /api/education

// Education Skills Endpoints
GET /api/educationSkills
GET /api/educationSkills/:username
POST /api/educationSkills/:username
PUT /api/educationSkills/:username
DELETE /api/educationSkills/:username
DELETE /api/education

// Contacts Endpoints
GET /api/contacts
GET /api/contacts/:username
POST /api/contacts/:username
PUT /api/contacts/:username
DELETE /api/contacts/:username
DELETE /api/contacts

// Post Endpoints
GET /api/posts
GET /api/posts/:username
POST /api/posts/:username

// Video File Endpoints
GET /api/videoFiles
GET /api/videoFiles/:postID
POST /api/videoFiles/:postID

// Photo Files Endpoints
GET /api/photoFiles
GET /api/photoFiles/:postID
POST /api/photoFiles/:postID

// PDF Files Endpoints
GET /api/pdfFiles
GET /api/pdfFiles/:postID
POST /api/pdfFiles/:postID

// Comments Endpoints
GET /api/comments
GET /api/comments/:postID
POST /api/comments/:postID

// Direct Message Endpoints
GET /api/directMessage
GET /api/directMessage/:username
POST /api/directMessage/:username

// Follow Endpoints
GET /api/follow
GET /api/follow/:username
POST /api/follow
DELETE /api/follow/:username

// Connect Endpoints
GET /api/connect
GET /api/connect/:username
POST /api/connect
DELETE /api/connect/:username

// Login Endpoint
POST /api/login

// Like Endpoints
GET /api/likes
GET /api/likes/:postID
POST /api/likes/:postID
DELETE /api/likes/:postID
```

## Screenshots

| Signup | Login |
| :---: | :----: |
|<img width="2560" alt="signup" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/signup.png"> | <img width="2560" alt="login" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/login.png"> |
|  |  |
| <img width="2560" alt="profile1" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/profile1.png"> | <img width="2560" alt="profile2" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/profile2.png"> |
| <img width="2560" alt="infoFill" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/infoFill.png"> | <img width="2560" alt="follows" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/follows.png"> |
| <img width="2560" alt="notifications" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/notifications.png"> | <img width="2560" alt="search" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/search.png"> |
| <img width="2560" alt="posts" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/posts.png"> | <img width="2560" alt="comment" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/comment.png"> |
| <img width="2560" alt="DM1" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/DM1.png"> | <img width="2560" alt="DM2" src="https://github.com/ashkanchaji/AP_Project_Linked/blob/a57800001a6903adb42a6c9375b4fc1779cb750e/Documents/Screenshots/DM2.png"> |

## Technologies Used

- Java
- JavaFX
- MySQL
- HTTP Server
- RESTful API
- JWT authentication
- MVC Architecture
- JSON
- DAO design pattern

## License

Project Linked is licensed under the MIT License. You are free to modify and distribute the project according to the terms of the license.

## Contributing

Contributions to Project Linked are welcome! If you want to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure that the codebase passes all tests.
4. Submit a pull request describing your changes.
