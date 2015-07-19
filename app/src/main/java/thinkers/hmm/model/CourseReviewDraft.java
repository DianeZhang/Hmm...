package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class CourseReviewDraft extends ReviewDraft {

    private int cid;

    public CourseReviewDraft(int cid) {
        super();
        this.cid = cid;
    }

    public CourseReviewDraft() {
        super();
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
