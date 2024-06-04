package org.Linked.server.Model;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;

import java.sql.Date;

public class Repost extends Post{

    private String repostId;

    public Repost(int type, String postId ,String text , int likes , int comments, Date createdAt , int reposts , String repostId) throws CharacterNumberLimitException {
        super(type, postId ,text , likes ,comments , createdAt , reposts);
        this.repostId = repostId;
    }

    public Repost(String posterID, String text, int likes, int comments, Date createdAt, int reposts, String byteFilePath, String repostId) {
        super(posterID, text, likes, comments, createdAt, reposts, byteFilePath);
        this.repostId = repostId;
    }

    public String getRepostId() {
        return repostId;
    }

    public void setRepostId(String repostId) {
        this.repostId = repostId;
    }

    @Override
    public String toString(){
        return "(Repost)" +
                "{RepostId :" + repostId +"}";
    }
}
