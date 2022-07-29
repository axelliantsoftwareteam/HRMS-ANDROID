
package com.example.hrm.Model.BasicSetup.Designation;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetDesignationData> getDesignationData = null;

    public List<GetDesignationData> getResponse() {
        return getDesignationData;
    }

    public void setResponse(List<GetDesignationData> getDesignationData) {
        this.getDesignationData = getDesignationData;
    }

}
