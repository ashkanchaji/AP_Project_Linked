package org.example.Model;

public class Like {

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


    @Override
    public String toString() {
        return "(Like)" +
                "{user " + this.liker +
                 "Liked " + this.liked;
    }
}
