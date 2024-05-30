package org.example.Controller.DAO;

import org.example.Model.Post;

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
            ps.executeUpdate();
        }
    }

    public void deletePostByEmail(String email, String query) throws SQLException {
        deleteEntity(query, email);
    }

    public void deleteAllPosts(String query) throws SQLException {
        deleteAllEntities(query);
    }
}
