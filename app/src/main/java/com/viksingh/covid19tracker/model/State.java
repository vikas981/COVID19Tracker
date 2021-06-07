package com.viksingh.covid19tracker.model;

public class State {
    private String loc;
    private String confirmedCasesIndian;
    private String confirmedCasesForeign;
    private String totalConfirmed;
    private String deaths;
    private String discharged;



    public State() {
    }

    public State(String loc, String confirmedCasesIndian, String confirmedCasesForeign, String totalConfirmed, String deaths, String discharged) {
        this.loc = loc;
        this.confirmedCasesIndian = confirmedCasesIndian;
        this.confirmedCasesForeign = confirmedCasesForeign;
        this.totalConfirmed = totalConfirmed;
        this.deaths = deaths;
        this.discharged = discharged;
    }

    public String getLoc() {
        return this.loc;
    }

    public void setLoc(String loc2) {
        this.loc = loc2;
    }

    public String getConfirmedCasesIndian() {
        return this.confirmedCasesIndian;
    }

    public void setConfirmedCasesIndian(String confirmedCasesIndian2) {
        this.confirmedCasesIndian = confirmedCasesIndian2;
    }

    public String getConfirmedCasesForeign() {
        return this.confirmedCasesForeign;
    }

    public void setConfirmedCasesForeign(String confirmedCasesForeign2) {
        this.confirmedCasesForeign = confirmedCasesForeign2;
    }

    public String getTotalConfirmed() {
        return this.totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed2) {
        this.totalConfirmed = totalConfirmed2;
    }

    public String getDeaths() {
        return this.deaths;
    }

    public void setDeaths(String deaths2) {
        this.deaths = deaths2;
    }

    public String getDischarged() {
        return this.discharged;
    }

    public void setDischarged(String discharged2) {
        this.discharged = discharged2;
    }
}
