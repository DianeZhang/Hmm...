package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public abstract class ReviewDraft {
    private int id;
    private int uid;
    private String title;
    private String content;

    public ReviewDraft(int id, int uid, String title, String content) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
    }

    public ReviewDraft(int uid, String title, String content) {
        this.id = -1;
        this.uid = uid;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
