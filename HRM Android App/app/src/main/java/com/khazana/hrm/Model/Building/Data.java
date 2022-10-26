
package com.khazana.hrm.Model.Building;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetBuildingData> getBuildingData = null;

    public List<GetBuildingData> getResponse() {
        return getBuildingData;
    }

    public void setResponse(List<GetBuildingData> getBuildingData) {
        this.getBuildingData = getBuildingData;
    }

}
