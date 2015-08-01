package thinkers.hmm.model;

import java.io.Serializable;

/**
 * Created by chaoli on 7/18/15.
 */
public class CourseReviewDraft extends ReviewDraft implements Serializable {

    private int cid;

    public CourseReviewDraft(int cid, int id, int uid, String title, String content) {
        super(id, uid, title, content);
        this.cid = cid;
    }

    public CourseReviewDraft(int cid, int uid, String title, String content) {
        super(uid, title, content);
        this.cid = cid;
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
