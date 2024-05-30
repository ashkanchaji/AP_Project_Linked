package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Post extends Model{

    private String posterID;

    private String text;

    private int likes;

    private int comments;

    private Date createdAt;

    private int reposts;

    private String byteFilePath;

    public Post(String posterID, String text , int likes , int comments , Date createdAt , int reposts) throws CharacterNumberLimitException {
        this.posterID = posterID;
        if (text.length() > 3000)
            throw  new CharacterNumberLimitException();
        else
            this.text = text;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.reposts = reposts;
    }

    public String getUserId() {
        return posterID;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getReposts() {
        return reposts;
    }

    public String getByteFilePath() {
        return byteFilePath;
    }
    @Override
    public String toString(){
        return "(Post):" +
                "{ PostId: " + this.posterID + "/" +
                "CreatedAt: " + this.createdAt + "/"+
                "Text: " + this.text + "/"+
                "Likes: " + this.likes +"/"+
                "Comments: " + this.comments +"/"+
                "Reposts:" + this.reposts + "}";


    }
}
