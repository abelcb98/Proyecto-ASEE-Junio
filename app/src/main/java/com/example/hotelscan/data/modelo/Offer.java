
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rateCode")
    @Expose
    private String rateCode;
    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("guests")
    @Expose
    private Guests guests;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("rateFamilyEstimated")
    @Expose
    private RateFamilyEstimated rateFamilyEstimated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guests getGuests() {
        return guests;
    }

    public void setGuests(Guests guests) {
        this.guests = guests;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public RateFamilyEstimated getRateFamilyEstimated() {
        return rateFamilyEstimated;
    }

    public void setRateFamilyEstimated(RateFamilyEstimated rateFamilyEstimated) {
        this.rateFamilyEstimated = rateFamilyEstimated;
    }

}
