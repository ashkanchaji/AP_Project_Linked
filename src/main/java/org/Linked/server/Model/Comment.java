package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Comment extends  Post{

    private final String repliedUser;

    public Comment (int type, String posterID , String text , int likes , int comments , Date createdAt , int reposts , String repliedUser) throws CharacterNumberLimitException {
        super(posterID , text , likes , comments , createdAt ,reposts);
        if (repliedUser.length() > 1250)
            throw  new CharacterNumberLimitException();
        else
            this.repliedUser = repliedUser;
    }

    public Comment(String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath, String repliedUser) {
        super(posterID, text, likes, comments, createdAt, reposts, byteFilePath);
        this.repliedUser = repliedUser;
    }

    public Comment(String postId, String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath, String repliedUser) {
        super(postId, posterID, text, likes, comments, createdAt, reposts, byteFilePath);
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
