
package com.example.hrm.Model.Shifts;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetShiftData> getShiftData = null;

    public List<GetShiftData> getResponse() {
        return getShiftData;
    }

    public void setResponse(List<GetShiftData> getShiftData) {
        this.getShiftData = getShiftData;
    }

}
