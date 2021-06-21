
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("variations")
    @Expose
    private Variations variations;

    public String getCurrency() {
        if(currency==null) currency="";
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Variations getVariations() {
        return variations;
    }

    public void setVariations(Variations variations) {
        this.variations = variations;
    }

}
