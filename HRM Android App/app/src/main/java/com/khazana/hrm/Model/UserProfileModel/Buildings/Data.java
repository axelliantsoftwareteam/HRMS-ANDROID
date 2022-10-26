
package com.khazana.hrm.Model.UserProfileModel.Buildings;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetBuildingDataList> getBuildingDataList = null;

    public List<GetBuildingDataList> getResponse() {
        return getBuildingDataList;
    }

    public void setResponse(List<GetBuildingDataList> getBuildingDataList) {
        this.getBuildingDataList = getBuildingDataList;
    }

}
