package org.example.Model;

import org.example.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Comment extends  Post{

    private final String repliedUser;

    public Comment (String posterID , String text , int likes , int comments , Date createdAt , int reposts , String repliedUser) throws CharacterNumberLimitException {
        super(1, posterID , text , likes , comments , createdAt ,reposts);
        if (repliedUser.length() > 1250)
            throw  new CharacterNumberLimitException();
        else
            this.repliedUser = repliedUser;
    }

    public String getRepliedUser() {
        return repliedUser;
    }

    @Override
    public String toString(){
        return "(Comment)" +
                "{RepliedUser :" + repliedUser +"}";
    }
}
