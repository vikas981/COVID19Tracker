package com.viksingh.covid19tracker.model;

public class DistrictWiseModel {
    private String active;
    private String confirmed;
    private String deceased;
    private String district;
    private String newConfirmed;
    private String newDeceased;
    private String newRecovered;
    private String recovered;

    public DistrictWiseModel(String district2, String confirmed2, String active2, String recovered2, String deceased2, String newConfirmed2, String newRecovered2, String newDeceased2) {
        this.district = district2;
        this.confirmed = confirmed2;
        this.active = active2;
        this.recovered = recovered2;
        this.deceased = deceased2;
        this.newConfirmed = newConfirmed2;
        this.newRecovered = newRecovered2;
        this.newDeceased = newDeceased2;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getConfirmed() {
        return this.confirmed;
    }

    public String getActive() {
        return this.active;
    }

    public String getRecovered() {
        return this.recovered;
    }

    public String getDeceased() {
        return this.deceased;
    }

    public String getNewConfirmed() {
        return this.newConfirmed;
    }

    public String getNewRecovered() {
        return this.newRecovered;
    }

    public String getNewDeceased() {
        return this.newDeceased;
    }
}
