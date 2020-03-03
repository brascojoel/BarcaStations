package com.example.barcastations;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("id")
    private String id;

    @SerializedName("street_name")
    private String street_name;

    @SerializedName("city")
    private String city;

    @SerializedName("utm_x")
    private String utm_x;

    @SerializedName("utm_y")
    private String utm_y;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lon")
    private String lon;

    @SerializedName("furniture")
    private String furniture;

    @SerializedName("buses")
    private String buses;

    @SerializedName("distance")
    private String distance;

    public Place(String id, String street_name, String city, String utm_x, String utm_y, String lat, String lon, String furniture, String buses, String distance) {
        this.id = id;
        this.street_name = street_name;
        this.city = city;
        this.utm_x = utm_x;
        this.utm_y = utm_y;
        this.lat = lat;
        this.lon = lon;
        this.furniture = furniture;
        this.buses = buses;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUtm_x() {
        return utm_x;
    }

    public void setUtm_x(String utm_x) {
        this.utm_x = utm_x;
    }

    public String getUtm_y() {
        return utm_y;
    }

    public void setUtm_y(String utm_y) {
        this.utm_y = utm_y;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public String getBuses() {
        return buses;
    }

    public void setBuses(String buses) {
        this.buses = buses;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
