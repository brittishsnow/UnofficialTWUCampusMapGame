package com.maxstow.unofficialtwucampusmapgame;

//Each row in the database can be represented by an object
//Columns will represent the objects properties
public class Locations {

    private int _id;
    private String _locationName;
    private double _longitude;
    private double _latitude;


    public Locations(){
    }

    public Locations(String locationName){
        this._locationName = locationName;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_locationname(String _locationName) {
        this._locationName = _locationName;
    }

    public int get_id() {
        return _id;
    }

    public String get_locationname() {
        return _locationName;
    }

    public double get_longitude() {
        return _longitude;
    }

    public double get_latitude() {
        return _latitude;
    }

}