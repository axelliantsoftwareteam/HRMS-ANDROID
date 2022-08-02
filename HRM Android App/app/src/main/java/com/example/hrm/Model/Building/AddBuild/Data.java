
package com.example.hrm.Model.Building.AddBuild;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private AddBuildData addBuildData;

    public AddBuildData getResponse() {
        return addBuildData;
    }

    public void setResponse(AddBuildData addBuildData) {
        this.addBuildData = addBuildData;
    }

}
