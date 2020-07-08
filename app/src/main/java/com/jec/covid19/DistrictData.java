package com.jec.covid19;

public class DistrictData {
    private String dtname;
    private String active;
    private String confirmed;
    private String deceased;
    private String recovered;

    public DistrictData(String dtname, String active, String confirmed, String deceased, String recovered) {
        this.dtname = dtname;
        this.active = active;
        this.confirmed = confirmed;
        this.deceased = deceased;
        this.recovered = recovered;
    }

    public String getDtname() {
        return dtname;
    }

    public String getActive() {
        return active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getRecovered() {
        return recovered;
    }
}
