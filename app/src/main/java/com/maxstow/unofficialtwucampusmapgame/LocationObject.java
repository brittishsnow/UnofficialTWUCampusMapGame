package com.maxstow.unofficialtwucampusmapgame;

public class LocationObject {
    /**
     * Declare the private variables for the Location object
     */
    private double longitudeValue;
    private double latitudeValue;
    private String buildingName;

    /**
     * Assign the values of each attribute to the Location Object
     * @param buildingName //The name of the building
     * @param latitudeValue //The latitude value of the coordinate for this building
     * @param longitudeValue //The longitude value of the coordinate for this building
     */
    public LocationObject(String buildingName, double latitudeValue, double longitudeValue) {
        this.buildingName = buildingName;
        this.latitudeValue = latitudeValue;
        this.longitudeValue = longitudeValue;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public double getLongitudeValue() {
        return longitudeValue;
    }

    public double getLatitudeValue() {
        return latitudeValue;
    }
}
