package org.server.Model;

import org.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Comment extends  Post{

    private final String repliedUser;

    public Comment (int type, String posterID , String text , int likes , int comments , Date createdAt , int reposts , String repliedUser) throws CharacterNumberLimitException {
        super(type, posterID , text , likes , comments , createdAt ,reposts);
        if (repliedUser.length() > 1250)
            throw  new CharacterNumberLimitException();
        else
            this.repliedUser = repliedUser;
    }

    public Comment(String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath, String repliedUser) {
        super(posterID, text, likes, comments, createdAt, reposts, byteFilePath);
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
