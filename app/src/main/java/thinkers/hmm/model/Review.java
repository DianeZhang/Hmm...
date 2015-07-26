package thinkers.hmm.model;

import java.util.Date;

/**
 * Created by chaoli on 7/18/15.
 */
public abstract class Review {
    private int id = -1;
    private int uid;
    private int like;
    private int dislike;
    private String title;
    private String content;
    private String location;
    private Date createdtime;

    public Review(int id, int uid, String title, String content, String location, Date createdtime) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.location = location;
        this.createdtime = createdtime;
    }

    public Review(int id, int uid, int likes, int dislikes, String title, String content, String location, Date createdtime) {
        this.id = id;
        this.uid = uid;
        this.like = likes;
        this.dislike = dislikes;
        this.title = title;
        this.content = content;
        this.location = location;
        this.createdtime = createdtime;
    }

    public Review(int uid, String title, String content, String location, Date createdtime) {
        this.id = -1;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.location = location;
        this.createdtime = createdtime;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLocation() { return this.location; }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLocation(String location) { this.location = location; }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
}
