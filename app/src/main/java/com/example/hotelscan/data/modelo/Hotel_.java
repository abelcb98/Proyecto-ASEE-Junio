
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hotel_ {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("hotelId")
    @Expose
    private String hotelId;
    @SerializedName("chainCode")
    @Expose
    private String chainCode;
    @SerializedName("dupeId")
    @Expose
    private String dupeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("hotelDistance")
    @Expose
    private HotelDistance hotelDistance;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("amenities")
    @Expose
    private List<String> amenities = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    @SerializedName("description")
    @Expose
    private Description description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getDupeId() {
        return dupeId;
    }

    public void setDupeId(String dupeId) {
        this.dupeId = dupeId;
    }

    public String getName() {
        if (name==null){
            name="";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        if (rating==null){rating="¿?";}
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public HotelDistance getHotelDistance() {
        return hotelDistance;
    }

    public void setHotelDistance(HotelDistance hotelDistance) {
        this.hotelDistance = hotelDistance;
    }

    public Address getAddress() {
        if (address==null)
            return new Address();
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        if (contact==null) return new Contact();
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<Medium> getMedia() {
        if (media==null){return new ArrayList<Medium>();}
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

}
