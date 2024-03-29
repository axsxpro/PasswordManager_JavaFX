package org.example.ex_application_bureau.Model;

public class passwordManager {

    private String username;
    private String password;
    private String url;
    private User user;


    // Constructeur
    public passwordManager(String username, String password, String url, User user) {

        this.username = username;
        this.password = password;
        this.url = url;
        this.user = user;

    }

    // Getters et Setters
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public User getUser() {
        return user;
    }
    public void setUrl(User user) {
        this.user = user;
    }

}