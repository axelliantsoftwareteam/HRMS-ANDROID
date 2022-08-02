
package com.example.hrm.Model.BasicSetup.Department.AddDepart;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private AddDepartData addDepartData;

    public AddDepartData getResponse() {
        return addDepartData;
    }

    public void setResponse(AddDepartData addDepartData) {
        this.addDepartData = addDepartData;
    }

}
