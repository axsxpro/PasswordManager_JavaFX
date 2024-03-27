package org.example.ex_application_bureau;

public class Users {

    private String password;
    private String username;
    private String url;


    // Constructeur
    public Users(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;

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

}