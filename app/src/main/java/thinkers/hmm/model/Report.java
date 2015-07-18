package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class Report {

    private int id;
    private int uid;
    private String title;
    private String content;


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
