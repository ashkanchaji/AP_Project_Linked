package org.Linked.client.Model;

public class Like extends Model {

    private String liker;

    private String liked;

    public Like (String liker , String liked){
        this.liker = liker;
        this.liked = liked;
    }

    public String getLiker() {
        return liker;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "(Like)" +
                "{user " + this.liker +
                 "Liked " + this.liked;
    }
}
