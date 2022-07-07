
package com.example.hrm.Model.GetAllTask;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<Response> response = null;
    @SerializedName("counts_info")
    @Expose
    private CountsInfo countsInfo;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public CountsInfo getCountsInfo() {
        return countsInfo;
    }

    public void setCountsInfo(CountsInfo countsInfo) {
        this.countsInfo = countsInfo;
    }

}
