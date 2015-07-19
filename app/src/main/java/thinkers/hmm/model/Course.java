package thinkers.hmm.model;

/**
 * Created by chaoli on 7/18/15.
 */
public class Course {
    private int id;
    private String name;
    private String code;
    private String school;


    public Course(int id, String name, String code, String school) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.school = school;
    }

    public Course(String name, String code, String school) {
        this.id = -1;
        this.name = name;
        this.code = code;
        this.school = school;
    }

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
