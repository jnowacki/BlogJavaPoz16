package pl.jnowacki.model;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String token;
    private boolean active;

    public User() {
    }

    public User(Long id, String username, String password, String token, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
