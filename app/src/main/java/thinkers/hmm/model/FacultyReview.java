package thinkers.hmm.model;

import java.util.Date;

/**
 * Created by chaoli on 7/18/15.
 */
public class FacultyReview extends Review {

    private int fid;

    public FacultyReview(int id, int uid, int fid, String title, String content, String location, Date createdtime) {
        super(id, uid, title, content, location, createdtime);
        this.fid = fid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
