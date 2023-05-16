package com.retarus.fax.base.rest;

/**
 * @author thiagon
 * <p>
 * Enum for the fax regions, each represent a different RetarusFax fax server.
 * <p>
 * Possible values are:<p>
 * FRANKFURT: Frankfurt<p>
 * MUNICH: Munich<p>
 * SECAUCUS: Secaucus<p>
 * ASHBURN: Ashburn<p>
 * SWITZERLAND: Switzerland<p>
 * SINGAPORE: Singapore
 */
public enum Location implements URLProvider {
    FRANKFURT("https://faxws.de1.retarus.com/rest/v1"),
    MUNICH("https://faxws.de2.retarus.com/rest/v1"),
    SECAUCUS("https://faxws.us2.retarus.com/rest/v1"),
    ASHBURN("https://faxws.us1.retarus.com/rest/v1"),
    ZURICH("https://faxws.ch1.retarus.com/rest/v1"),
    SINGAPORE("https://faxws.sg1.retarus.com/rest/v1");

    private final String locationUrl;

    Location(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    @Override
    public String getSendUrl() {
        return locationUrl;
    }

    @Override
    public String getFetchUrl() {
        return locationUrl;
    }

    @Override
    public Location[] getLocations() {
        return new Location[]{this};
    }
}
