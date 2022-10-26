
package com.khazana.hrm.Model.SpinerModel;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<SpinerResponse> spinerResponse = null;

    public List<SpinerResponse> getResponse() {
        return spinerResponse;
    }

    public void setResponse(List<SpinerResponse> spinerResponse) {
        this.spinerResponse = spinerResponse;
    }

}
