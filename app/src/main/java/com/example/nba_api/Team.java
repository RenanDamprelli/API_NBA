package com.example.nba_api;

import java.io.Serializable;

public class Team implements Serializable {

    private String tmId;
    private String Abreviation;
    private String City;
    private String Conference;
    private String FullName;

    public String gettmId() {
        return tmId;
    }

    public void settmId(String tmId) {
        this.tmId = tmId;
    }

    public String getAbreviation() {
        return Abreviation;
    }

    public void setAbreviation(String Abreviation) {
        this.Abreviation = Abreviation;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getConference() {
        return Conference;
    }

    public void setConference(String Conference) {
        this.Conference = Conference;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    @Override
    public String toString(){
        return
                "\n ID: " + gettmId()
                + "\n" + getAbreviation()
                + "\n" + getCity()
                + "\n" + getConference()
                + "\n" + getFullName()
                +"\n";
    }
}
