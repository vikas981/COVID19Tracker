package com.viksingh.covid19tracker.model;

public class StateWiseModel {
    private String active;
    private String confirmed;
    private String confirmed_new;
    private String death;
    private String death_new;
    private String lastupdate;
    private String recovered;
    private String recovered_new;
    private String state;

    public StateWiseModel(String state2, String confirmed2, String confirmed_new2, String active2, String death2, String death_new2, String recovered2, String recovered_new2, String lastupdate2) {
        this.state = state2;
        this.confirmed = confirmed2;
        this.confirmed_new = confirmed_new2;
        this.active = active2;
        this.death = death2;
        this.death_new = death_new2;
        this.recovered = recovered2;
        this.recovered_new = recovered_new2;
        this.lastupdate = lastupdate2;
    }

    public String getState() {
        return this.state;
    }

    public String getConfirmed() {
        return this.confirmed;
    }

    public String getConfirmed_new() {
        return this.confirmed_new;
    }

    public String getActive() {
        return this.active;
    }

    public String getDeath() {
        return this.death;
    }

    public String getDeath_new() {
        return this.death_new;
    }

    public String getRecovered() {
        return this.recovered;
    }

    public String getRecovered_new() {
        return this.recovered_new;
    }

    public String getLastupdate() {
        return this.lastupdate;
    }
}
