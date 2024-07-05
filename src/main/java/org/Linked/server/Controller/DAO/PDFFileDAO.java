package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.PDFFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class PDFFileDAO extends GenericDAO<PDFFile> {

    private final String CREATE_PDF_FILE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "postId VARCHAR(36) NOT NULL, "
            + "pdfFile LONGBLOB NOT NULL"
            + ")";

    private final String INSERT_PDF_FILE_SQL = "INSERT INTO " + tablePath
            + "(postId, pdfFile) VALUES (?, ?)";

    public PDFFileDAO() {
        super("pdf_files");
    }

    @Override
    protected PDFFile mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String postId = resultSet.getString("postId");
        byte[] pdfBytes = resultSet.getBytes("pdfFile");
        String pdfFile = Base64.getEncoder().encodeToString(pdfBytes);
        return new PDFFile(postId, pdfFile);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_PDF_FILE_TABLE_SQL;
    }

    public void savePDFFile(PDFFile pdfFile) throws SQLException {
        checkTableExistence();
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PDF_FILE_SQL)) {
            ps.setString(1, pdfFile.getPostId());
            ps.setBytes(2, Base64.getDecoder().decode(pdfFile.getPdfFile()));
            ps.executeUpdate();
        }
    }

    public PDFFile getPDFFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE postId = ?";
        return getEntity(query, postId);
    }

    public void deletePDFFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE postId = ?";
        deleteEntity(query, postId);
    }
}
