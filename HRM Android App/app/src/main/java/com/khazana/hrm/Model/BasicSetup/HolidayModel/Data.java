
package com.khazana.hrm.Model.BasicSetup.HolidayModel;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetHolidayData> getHolidayData = null;

    public List<GetHolidayData> getResponse() {
        return getHolidayData;
    }

    public void setResponse(List<GetHolidayData> getHolidayData) {
        this.getHolidayData = getHolidayData;
    }

}
