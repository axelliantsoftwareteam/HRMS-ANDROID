
package com.example.hrm.Adapter.Profile.SystemBuilding;

import java.util.List;
import javax.annotation.Generated;

;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetSystemData> getSystemData = null;

    public List<GetSystemData> getResponse() {
        return getSystemData;
    }

    public void setResponse(List<GetSystemData> getSystemData) {
        this.getSystemData = getSystemData;
    }

}
