package thinkers.hmm.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String username;
    private String email;
    private String password;
    private Date lastlogin;

    public User(int id, String username, String email, String password, Date lastlogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastlogin = lastlogin;
    }

    public User(String username, String email, String password, Date lastlogin) {
        this.id = -1;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastlogin = lastlogin;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }
}
