package com.supersub.noamm_000.tincherfirsttry;

import java.io.Serializable;

/**
 * Created by noamm_000 on 11/08/2016.
 */
public class Principal extends Person implements Serializable {
    public enum SchoolCity {
        Raanana,
        Herzeliya,
        Beit_Shaan
    }
    public enum SchoolName {
        Dekel,
        Ostrovski,
        Tichon_Hadash
    }

    private SchoolCity schoolCity;
    private SchoolName schoolName;

    public Principal(String firstName, String lastName, String username, String pass,
                     SchoolCity schoolCity, SchoolName schoolName) {
        super(firstName, lastName, username, pass);
        this.schoolCity = schoolCity;
        this.schoolName = schoolName;
    }

    public SchoolCity getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(SchoolCity schoolCity) {
        this.schoolCity = schoolCity;
    }

    public SchoolName getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(SchoolName schoolName) {
        this.schoolName = schoolName;
    }
}
