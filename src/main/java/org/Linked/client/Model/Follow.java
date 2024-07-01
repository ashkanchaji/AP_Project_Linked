package org.Linked.client.Model;

public class Follow extends Model {

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

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    @Override
    public String toString(){
        return "(Follow)" +
                " { follower : " + this.follower +
                "following : " + this.following + "}";
    }
}
