
package com.example.hrm.Model.GetAllTask;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetAllTaskData> getAllTaskData = null;
    @SerializedName("counts_info")
    @Expose
    private CountsInfo countsInfo;

    public List<GetAllTaskData> getResponse() {
        return getAllTaskData;
    }

    public void setResponse(List<GetAllTaskData> getAllTaskData) {
        this.getAllTaskData = getAllTaskData;
    }

    public CountsInfo getCountsInfo() {
        return countsInfo;
    }

    public void setCountsInfo(CountsInfo countsInfo) {
        this.countsInfo = countsInfo;
    }

}
