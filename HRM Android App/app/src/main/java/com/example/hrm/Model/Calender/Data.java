
package com.example.hrm.Model.Calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetCalenderInfoData> getCalenderInfoData = null;

    public List<GetCalenderInfoData> getResponse() {
        return getCalenderInfoData;
    }

    public void setResponse(List<GetCalenderInfoData> getCalenderInfoData) {
        this.getCalenderInfoData = getCalenderInfoData;
    }

}
