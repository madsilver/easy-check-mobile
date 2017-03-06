package br.com.silver.easycheck.model;

import java.io.Serializable;

/**
 * Created by silver on 25/04/16.
 */
public class User implements Serializable {

    public String username;
    public String password;
    public User(){}
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
