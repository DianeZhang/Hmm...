package thinkers.hmm.model;

import java.util.Date;

/**
 * Created by chaoli on 7/18/15.
 */
public class CourseReview extends Review {

    private int cid = -1;

    public CourseReview(int id, int cid, int uid, String title, String content, String location, Date createdtime) {
        super(id, uid, title, content, location, createdtime);
        this.cid = cid;
    }

    public CourseReview(int id, int cid, int uid, int likes, int dislikes, String title, String content, String location, Date createdtime) {
        super(id, uid, likes, dislikes, title, content, location, createdtime);
        this.cid = cid;
    }

    public CourseReview(int cid, int uid, String title, String content, String location, Date createdtime) {
        super(uid, title, content, location, createdtime);
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
