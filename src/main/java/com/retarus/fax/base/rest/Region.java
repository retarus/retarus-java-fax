package com.retarus.fax.base.rest;

import java.util.Random;

import static com.retarus.fax.base.rest.Location.*;

public enum Region implements URLProvider {
    EUROPE("https://faxws-ha.de.retarus.com/rest/v1", FRANKFURT, MUNICH),
    USA("https://faxws-ha.us.retarus.com/rest/v1", SECAUCUS, ASHBURN),
    ASIA("https://faxws-ha.sg.retarus.com/rest/v1", SINGAPORE),
    SWITZERLAND("https://faxws-ha.ch.retarus.com/rest/v1", ZURICH);
    private static final Random RANDOM = new Random();
    private final String sendURL;
    private final Location[] locations;

    Region(String sendURL, Location... locations) {
        this.sendURL = sendURL;
        this.locations = locations;
    }

    public Location[] getLocations() {
        return locations;
    }

    @Override
    public String getSendUrl() {
        return sendURL;
    }

    @Override
    public String getFetchUrl() {
        Location location = locations[RANDOM.nextInt(locations.length)];
        return location.getFetchUrl();
    }
}
