package com.supersub.noamm_000.tincherfirsttry;

import java.io.Serializable;

/**
 * Created by noamm_000 on 23/07/2016.
 */
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Person(String first, String last, String user, String pass) {
        firstName = first;
        lastName = last;
        userName = user;
        password = pass;
    }
    public Person(String first, String last) {
        firstName = first;
        lastName = last;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public  String getLastName() {
        return this.lastName;
    }

    public String getUserName()  { return userName; }

    public String getPassword() { return password; }
}
