package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class Course {
    private int id;
    private String name;
    private String code;
    private String school;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSchool() {
        return school;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
