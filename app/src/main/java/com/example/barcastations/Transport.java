package com.example.barcastations;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transport {
    @SerializedName("nearstations")
    private List<Station> nearstations;

    @SerializedName("transport")
    private String type;


    public Transport(List<Station> nearstations, String type) {
        this.nearstations = nearstations;
        this.type = type;
    }

    public List<Station> getNearstations() {
        return nearstations;
    }

    public void setNearstations(List<Station> nearstations) {
        this.nearstations = nearstations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
