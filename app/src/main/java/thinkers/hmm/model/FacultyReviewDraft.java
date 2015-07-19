package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class FacultyReviewDraft extends ReviewDraft {

    private int fid;

    public FacultyReviewDraft(int reviewId, int uid, int fid, String title, String content) {
        super(reviewId, uid, title, content);
        this.fid = fid;
    }

    public FacultyReviewDraft(int uid, int fid, String title, String content) {
        super(uid, title, content);
        this.fid = fid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
