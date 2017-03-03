package com.supersub.noamm_000.tincherfirsttry;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by noamm_000 on 23/07/2016.
 */
public class Teacher extends Person implements Serializable {

    public enum Education {
        None,
        High_school_Diploma,
        First_Degree,
        Second_Degree_Or_Above;
    }


    public enum AOI {
        HaSharon, HaMerkaz, HaShfela, HaTzafon, HaDarom
    }
    public enum SchoolType {
        Nevermind, State, Democratic, Religious
    }

    private int age;
    private Education education;
    private AOI aoi;
    private SchoolType schoolType;
    private boolean isEducationalDegree;
    private String address;
    private String mail;
    private String phone;
    private int viewsNum;

    public Teacher(String first, String last, String user, String pass, int age,
                    Education education, AOI aoi, SchoolType schoolType,
                    boolean isEducationalDegree, String address, String mail ) {
        super(first, last, user, pass);
        this.age = age;
        this.isEducationalDegree = isEducationalDegree;
        this.education = education;
        this.aoi = aoi;
        this.schoolType = schoolType;
        this.address = address;
        this.mail = mail;
    }

    public int getViewsNum() {
        return viewsNum;
    }

    public Teacher(String first, String last, String user, String pass, int age,
                   Education education, AOI aoi, SchoolType schoolType,
                   boolean isEducationalDegree, String address, String mail, String phone, int viewsNum ) {
        super(first, last, user, pass);
        this.age = age;
        this.isEducationalDegree = isEducationalDegree;
        this.education = education;
        this.aoi = aoi;
        this.schoolType = schoolType;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.viewsNum = viewsNum;

    }

    public Teacher(String first, String last, int age, AOI aoi) {
        super(first, last);
        this.age = age;
        this.aoi = aoi;
    }

    public Teacher(String first, String last, int age,Education education, AOI aoi) {
        super(first, last);
        this.age = age;
        this.education = education;
        this.aoi = aoi;
    }

    public int getAge() {
        return age;
    }

    public Education getEducation() {
        return education;
    }

    public AOI getAoi() {
        return aoi;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public boolean isEducationalDegree() {
        return isEducationalDegree;
    }

    public String getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String toString() {
        return "Hello, I'm " + this.getFirstName() + " " + this.getLastName() + " " + this.getUserName() +
                " " + this.getPassword() + " " + this.age + " " + this.education + " " + this.aoi +
                " " + this.schoolType + " " + this.isEducationalDegree() + " " + this.address +
                " " + this.mail + " " + this.phone;
    }
    public void sayHello(Context ctx) {
        Toast.makeText(ctx, this.toString(), Toast.LENGTH_LONG).show();
    }

}
