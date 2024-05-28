package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.util.Date;

public class Post {

    private String postId;

    private String text;

    private int likes;

    private int comments;

    private Date createdAt;

    private int reposts;

    public Post(String postId , String text , int likes , int comments , Date createdAt , int reposts) throws CharacterNumberLimitException {
        this.postId = postId;
        if (text.length() > 3000)
            throw  new CharacterNumberLimitException();
        else
            this.text = text;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.reposts = reposts;
    }

    public Post(){
        //کدهای سال بالایی ها رو دیدم همشون یه کانستراکتور خالی داشتن برا اورلود نمدونم چرا
    }

    public String getUserId() {
        return postId;
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

    @Override
    public String toString(){
        return "(Post):" +
                "{ PostId: " + this.postId + "/" +
                "CreatedAt: " + this.createdAt + "/"+
                "Text: " + this.text + "/"+
                "Likes: " + this.likes +"/"+
                "Comments: " + this.comments +"/"+
                "Reposts:" + this.reposts + "}";

    }
}
