
package com.example.hrm.Model.CalenderInfo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
