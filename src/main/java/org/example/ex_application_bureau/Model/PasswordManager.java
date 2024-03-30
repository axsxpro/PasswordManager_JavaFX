package org.example.ex_application_bureau.Model;

public class PasswordManager {

    private int idPasswordManager;
    private String username;
    private String password;
    private String url;
    private User idUser;


    // Constructeur
    public PasswordManager(int idPasswordManager, String username, String password, String url, User idUser) {

        this.idPasswordManager = idPasswordManager;
        this.username = username;
        this.password = password;
        this.url = url;
        this.idUser = idUser;

    }

    // Getters et Setters
    public int getIdPasswordManager() {
        return idPasswordManager;
    }
    public void setIdPasswordManager(int idPasswordManager) {
        this.idPasswordManager = idPasswordManager;
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

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return idUser;
    }
    public void setUrl(User idUser) {
        this.idUser = idUser;
    }

}