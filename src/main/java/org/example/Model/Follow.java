package org.example.Model;

public class Follow {

    private String follower;

    private String following;

    public Follow(String follower , String following){
        this.follower = follower;
        this.following = following;
    }

    public String getFollower() {
        return follower;
    }

    public String getFollowing() {
        return following;
    }

    @Override
    public String toString(){
        return "(Follow)" +
                " { follower : " + this.follower +
                "following : " + this.following + "}";
    }
}
