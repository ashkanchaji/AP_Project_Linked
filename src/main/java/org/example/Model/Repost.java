package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Repost extends Post{

    private String repostId;

    public Repost(String postId ,String text , int likes , int comments, Date createdAt , int reposts , String repostId) throws CharacterNumberLimitException {
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
