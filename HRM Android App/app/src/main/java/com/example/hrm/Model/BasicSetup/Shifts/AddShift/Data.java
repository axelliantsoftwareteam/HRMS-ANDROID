
package com.example.hrm.Model.BasicSetup.Shifts.AddShift;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private GetAddShift getAddShift;

    public GetAddShift getResponse() {
        return getAddShift;
    }

    public void setResponse(GetAddShift getAddShift) {
        this.getAddShift = getAddShift;
    }

}
