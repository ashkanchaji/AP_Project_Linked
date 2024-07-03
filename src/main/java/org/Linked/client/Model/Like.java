package org.Linked.client.Model;

public class Like extends Model {

    private String liker;
    private String liked;
    private String postId;

    public Like(String liker, String liked, String postId) {
        this.liker = liker;
        this.liked = liked;
        this.postId = postId;
    }

    public String getLiker() {
        return liker;
    }

    public String getLiked() {
        return liked;
    }

    public String getPostId() {
        return postId;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "(Like)" +
                "{user " + this.liker +
                " liked " + this.liked +
                " on post " + this.postId + "}";
    }
}
