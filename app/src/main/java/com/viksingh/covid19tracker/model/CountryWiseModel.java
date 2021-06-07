package com.viksingh.covid19tracker.model;

public class CountryWiseModel {
    private String active;
    private String confirmed;
    private String country;
    private String deceased;
    private String flag;
    private String newConfirmed;
    private String newDeceased;
    private String recovered;
    private String tests;

    public CountryWiseModel(String country2, String confirmed2, String newConfirmed2, String active2, String deceased2, String newDeceased2, String recovered2, String tests2, String flag2) {
        this.country = country2;
        this.confirmed = confirmed2;
        this.newConfirmed = newConfirmed2;
        this.active = active2;
        this.deceased = deceased2;
        this.newDeceased = newDeceased2;
        this.recovered = recovered2;
        this.tests = tests2;
        this.flag = flag2;
    }

    public String getCountry() {
        return this.country;
    }

    public String getConfirmed() {
        return this.confirmed;
    }

    public String getNewConfirmed() {
        return this.newConfirmed;
    }

    public String getActive() {
        return this.active;
    }

    public String getDeceased() {
        return this.deceased;
    }

    public String getNewDeceased() {
        return this.newDeceased;
    }

    public String getRecovered() {
        return this.recovered;
    }

    public String getTests() {
        return this.tests;
    }

    public String getFlag() {
        return this.flag;
    }
}
