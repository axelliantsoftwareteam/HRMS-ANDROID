
package com.example.hrm.Model.Calender;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetCalenderData> getCalenderData = null;

    public List<GetCalenderData> getResponse() {
        return getCalenderData;
    }

    public void setResponse(List<GetCalenderData> getCalenderData) {
        this.getCalenderData = getCalenderData;
    }

}
