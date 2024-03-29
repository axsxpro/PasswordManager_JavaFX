package org.example.ex_application_bureau.Model;


public class User {

    private String email;
    private String password;


    public User(String username, String password) {

        this.email = username;
        this.password = password;
    }

    // Getters et Setters
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
