
package com.khazana.hrm.Model.BasicSetup.Designation;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private GetDesigData getDesigData;

    public GetDesigData getResponse() {
        return getDesigData;
    }

    public void setResponse(GetDesigData getDesigData) {
        this.getDesigData = getDesigData;
    }

}
