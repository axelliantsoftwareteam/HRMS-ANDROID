
package com.khazana.hrm.Model.AddRequestModel;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private AddLeaveResponse addLeaveResponse;

    public AddLeaveResponse getResponse() {
        return addLeaveResponse;
    }

    public void setResponse(AddLeaveResponse addLeaveResponse) {
        this.addLeaveResponse = addLeaveResponse;
    }

}
