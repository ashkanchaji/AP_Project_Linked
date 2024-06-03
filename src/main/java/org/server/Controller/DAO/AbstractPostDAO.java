package org.server.Controller.DAO;

import org.server.Controller.DB.MySqlDB;
import org.server.Model.Comment;
import org.server.Model.Post;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class AbstractPostDAO<T extends Post> extends GenericDAO<T> {
    protected AbstractPostDAO(String tableName) {
        super(tableName);
    }

    @Override
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    @Override
    protected abstract String getCreateTableSQL();

    public void savePost(T post, String query) throws SQLException {
        saveEntity(post, query, (ps, p) -> {
            ps.setString(1, p.getUserId());
            ps.setString(2, p.getText());
            ps.setInt(3, p.getLikes());
            ps.setInt(4, p.getComments());
            ps.setDate(5, p.getCreatedAt());
            ps.setInt(6, p.getReposts());
            ps.setString(7, p.getByteFilePath());
        });
    }

    public T getPostByEmail(String email, String query) throws SQLException {
        return getEntity(query, email);
    }

    public ArrayList<T> getAllPosts(String query) throws SQLException {
        return getAllEntities(query);
    }

    public void updatePost(T post, String query) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, post.getText());
            ps.setInt(2, post.getLikes());
            ps.setInt(3, post.getComments());
            ps.setDate(4, post.getCreatedAt());
            ps.setInt(5, post.getReposts());
            ps.setString(6, post.getByteFilePath());
            ps.setString(7, post.getUserId());

            if (post instanceof Comment){
                ps.setString(8, ((Comment) post).getRepliedUser());
            }
            ps.executeUpdate();
        }
    }

    public void deletePostByEmail(String email, Date date, String query) throws SQLException {
        checkTableExistence(); // ???

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setDate(2, date);
            statement.executeUpdate();
        }
    }

    public void deleteAllPosts(String query) throws SQLException {
        deleteAllEntities(query);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<Post> getPostsByHashTag(String hashtag) throws SQLException{
        String[] tableNames = {"posts", "reposts", "comments"};

        ArrayList<Post> posts = new ArrayList<>();

        PostDAO postDAO = new PostDAO();
        RepostDAO repostDAO = new RepostDAO();
        CommentDAO commentDAO = new CommentDAO();

        for (String tableName : tableNames){
            String query = "SELECT * FROM " + MySqlDB.getDBName() + "." + tableName;

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Post post = null;

                    switch (tableName) {
                        case "posts" :
                            post = postDAO.mapResultSetToEntity(resultSet);
                            break;
                        case "reposts" :
                            post = repostDAO.mapResultSetToEntity(resultSet);
                            break;
                        case "comments" :
                            post = commentDAO.mapResultSetToEntity(resultSet);
                            break;
                    }

                    if (post != null && post.getText().contains(hashtag)){
                        posts.add(post);
                    }
                }
            }
        }

        return posts;
    }
}
