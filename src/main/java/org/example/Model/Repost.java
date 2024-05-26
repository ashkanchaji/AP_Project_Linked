package org.example.Model;

import java.util.Date;

public class Repost extends Post{

    private String repostId;

    public Repost(String postId ,String text , int likes , int comments, Date createdAt , int reposts , String repostId){
        super(postId ,text , likes ,comments , createdAt , reposts);
        this.repostId = repostId;
    }

    public String getRepostId() {
        return repostId;
    }

    @Override
    public String toString(){
        return "(Repost)" +
                "{RepostId :" + repostId +"}";
    }
}
