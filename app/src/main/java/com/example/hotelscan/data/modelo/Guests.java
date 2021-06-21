
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guests {

    @SerializedName("adults")
    @Expose
    private Integer adults;

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

}
