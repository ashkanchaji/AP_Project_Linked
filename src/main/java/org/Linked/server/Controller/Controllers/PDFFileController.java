package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.DAO.PDFFileDAO;
import org.Linked.server.Model.PDFFile;

import java.sql.SQLException;
import java.util.Base64;

public class PDFFileController extends Controller {

    private static final PDFFileDAO pdfFileDAO = new PDFFileDAO();

    public static void savePDFFile(String postId, byte[] pdfFile) throws SQLException {
        PDFFile pdfFileObj = new PDFFile(postId, Base64.getEncoder().encodeToString(pdfFile));
        pdfFileDAO.savePDFFile(pdfFileObj);
    }

    public static String getPDFFile(String postId) throws SQLException {
        PDFFile pdfFile = pdfFileDAO.getPDFFileByPostId(postId);
        return pdfFile == null ? null : gson.toJson(pdfFile);
    }

    public static void deletePDFFile(String postId) throws SQLException {
        pdfFileDAO.deletePDFFileByPostId(postId);
    }
}
