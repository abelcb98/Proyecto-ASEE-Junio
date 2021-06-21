
package com.example.hotelscan.data.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variations {

    @SerializedName("average")
    @Expose
    private Average average;
    @SerializedName("changes")
    @Expose
    private List<Change> changes = null;

    public Average getAverage() {
        return average;
    }

    public void setAverage(Average average) {
        this.average = average;
    }

    public List<Change> getChanges() {
        return changes;
    }

    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }

}
