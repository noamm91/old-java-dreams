package com.supersub.noamm_000.tincherfirsttry;

/**
 * Created by noamm_000 on 25/08/2016.
 */
public class PrincipalAndTeachersPacket {
    private String[] cPrincipal;
    private Teacher[] allTheRest;

    public PrincipalAndTeachersPacket(String[] current, Teacher[] allTheRest) {
        this.cPrincipal = current;
        this.allTheRest = allTheRest;
    }

    public Teacher[] getAllTheRest() {

        return allTheRest;
    }

    public void setAllTheRest(Teacher[] allTheRest) {
        this.allTheRest = allTheRest;
    }

    public String[] getCPrincipal() {
        return cPrincipal;
    }

    public void setCPrincipal(String[] current) {
        this.cPrincipal = current;
    }
}
