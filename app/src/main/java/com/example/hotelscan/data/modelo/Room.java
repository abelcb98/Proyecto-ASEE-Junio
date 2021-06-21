
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typeEstimated")
    @Expose
    private TypeEstimated typeEstimated;
    @SerializedName("description")
    @Expose
    private Description_ description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TypeEstimated getTypeEstimated() {
        return typeEstimated;
    }

    public void setTypeEstimated(TypeEstimated typeEstimated) {
        this.typeEstimated = typeEstimated;
    }

    public Description_ getDescription() {
        return description;
    }

    public void setDescription(Description_ description) {
        this.description = description;
    }

}
