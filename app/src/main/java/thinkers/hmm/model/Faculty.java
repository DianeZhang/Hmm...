package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class Faculty {

    private int id;
    private String name;

    public Faculty(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
