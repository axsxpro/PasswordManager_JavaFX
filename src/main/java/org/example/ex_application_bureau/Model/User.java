package org.example.ex_application_bureau.Model;


public class User {

    private int idUser;
    private String email;
    private String password;


    public User(int idUser, String username, String password) {

        this.idUser = idUser;
        this.email = username;
        this.password = password;
    }

    // Getters et Setters

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

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


    public String toString() {
        return "ID user: " + idUser + ",  Email: " + email ;
    }


}
