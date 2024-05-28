package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.util.Date;

public class Comment extends  Post{

    private String repliedUser;

    public Comment (String postId , String text , int likes , int comments , Date createdAt , int reposts , String repliedUser) throws CharacterNumberLimitException {
        super(postId , text , likes , comments , createdAt ,reposts);
        if (repliedUser.length() > 1250)
            throw  new CharacterNumberLimitException();
        else
            this.repliedUser = repliedUser;
    }

    public String getComment() {
        return repliedUser;
    }

    @Override
    public String toString(){
        return "(Comment)" +
                "{RepliedUser :" + repliedUser +"}";
    }
}
