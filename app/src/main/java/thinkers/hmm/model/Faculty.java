package thinkers.hmm.model;

import java.io.Serializable;

public class Faculty implements Serializable {

    private int id;
    private String name;

    public Faculty(int id) {
        super();
        this.id = id;
    }

    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(int id, String name) {
        super();
        this.id = id;
        this.name =name;
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
