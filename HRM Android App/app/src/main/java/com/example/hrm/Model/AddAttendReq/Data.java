
package com.example.hrm.Model.AddAttendReq;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private AddAttendReq addAttendReq;

    public AddAttendReq getResponse() {
        return addAttendReq;
    }

    public void setResponse(AddAttendReq addAttendReq) {
        this.addAttendReq = addAttendReq;
    }

}
