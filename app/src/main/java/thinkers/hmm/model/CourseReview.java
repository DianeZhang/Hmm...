package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class CourseReview extends Review {

    private int cid = -1;

    CourseReview(int cid) {
        super(cid);
        this.cid = cid;
    }

    CourseReview() {
        super();
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
