
package com.example.hrm.Model.BasicSetup.StaticDataModel.AddStatic;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private GetAddStaticData getAddStaticData;

    public GetAddStaticData getResponse() {
        return getAddStaticData;
    }

    public void setResponse(GetAddStaticData getAddStaticData) {
        this.getAddStaticData = getAddStaticData;
    }

}
