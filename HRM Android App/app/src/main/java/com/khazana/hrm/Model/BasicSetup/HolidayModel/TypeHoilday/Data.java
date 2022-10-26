
package com.khazana.hrm.Model.BasicSetup.HolidayModel.TypeHoilday;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetHoildayListData> getHoildayListData = null;

    public List<GetHoildayListData> getResponse() {
        return getHoildayListData;
    }

    public void setResponse(List<GetHoildayListData> getHoildayListData) {
        this.getHoildayListData = getHoildayListData;
    }

}
