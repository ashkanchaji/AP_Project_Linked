package org.example.Model;

import java.sql.Date;

public class Post {

    private String userId;

    private String text;

    private int likes;

    private int comments;

    private Date createdAt;

    private int reposts;

    public Post(String userId , String text , int likes , int comments , Date createdAt , int reposts){
        this.userId = userId;
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
        return userId;
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
        return "post:" +
                "{ userId: " + this.userId + "/" +
                "createdAt: " + this.createdAt + "/"+
                "text: " + this.text + "/"+
                "likes: " + this.likes +"/"+
                "comments: " + this.comments +"/"+
                "reposts:" + this.reposts + "}";

    }
}
