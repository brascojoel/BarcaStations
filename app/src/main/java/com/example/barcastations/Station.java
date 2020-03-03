package com.example.barcastations;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/*
 * @SerializedName annotation is for json file
 * @PrimaryKey,@NonNull,@ColumnInfo annotations are for database sqllite
 */
@Entity(tableName = "station_table")
public class Station {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;
    @ColumnInfo(name = "street_name")
    @SerializedName("street_name")
    private String street_name;
    @ColumnInfo(name = "city")
    @SerializedName("city")
    private String city;
    @ColumnInfo(name = "utm_x")
    @SerializedName("utm_x")
    private String utm_x;
    @ColumnInfo(name = "utm_y")
    @SerializedName("utm_y")
    private String utm_y;
    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    private String lat;
    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    private String lon;
    @ColumnInfo(name = "furniture")
    @SerializedName("furniture")
    private String furniture;
    @ColumnInfo(name = "buses")
    @SerializedName("buses")
    private String buses;
    @ColumnInfo(name = "distance")
    @SerializedName("distance")
    private String distance;

    public Station(String id, String street_name, String city, String utm_x, String utm_y, String lat, String lon, String furniture, String buses, String distance) {
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
