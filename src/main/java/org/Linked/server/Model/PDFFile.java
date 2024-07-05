package org.Linked.server.Model;

import java.util.Base64;

public class PDFFile {
    private String postId;
    private String pdfFile; // Store as Base64 string

    public PDFFile(String postId, String pdfFile) {
        this.postId = postId;
        this.pdfFile = pdfFile;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public byte[] getPdfFileBytes() {
        return Base64.getDecoder().decode(pdfFile);
    }

    public void setPdfFileBytes(byte[] pdfFile) {
        this.pdfFile = Base64.getEncoder().encodeToString(pdfFile);
    }
}
