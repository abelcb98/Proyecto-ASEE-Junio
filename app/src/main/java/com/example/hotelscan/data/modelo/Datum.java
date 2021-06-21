
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("hotel")
    @Expose
    private Hotel_ hotel;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("self")
    @Expose
    private String self;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hotel_ getHotel() {
        return hotel;
    }

    public void setHotel(Hotel_ hotel) {
        this.hotel = hotel;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

}
