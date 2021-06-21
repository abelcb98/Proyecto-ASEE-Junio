
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("category")
    @Expose
    private String category;

    public String getUri() {
        if (uri==null)
            uri="";
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
