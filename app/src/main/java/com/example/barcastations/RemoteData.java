package com.example.barcastations;

import com.google.gson.annotations.SerializedName;

public class RemoteData {

    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private Transport transport;

    public RemoteData(String code, Transport transport) {
        this.code = code;
        this.transport = transport;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
