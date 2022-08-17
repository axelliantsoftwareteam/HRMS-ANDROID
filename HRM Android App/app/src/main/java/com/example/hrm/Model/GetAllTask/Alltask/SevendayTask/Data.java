
package com.example.hrm.Model.GetAllTask.Alltask.SevendayTask;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetSevendayData> getSevendayData = null;
    @SerializedName("counts_info")
    @Expose
    private CountsInfo countsInfo;

    public List<GetSevendayData> getResponse() {
        return getSevendayData;
    }

    public void setResponse(List<GetSevendayData> getSevendayData) {
        this.getSevendayData = getSevendayData;
    }

    public CountsInfo getCountsInfo() {
        return countsInfo;
    }

    public void setCountsInfo(CountsInfo countsInfo) {
        this.countsInfo = countsInfo;
    }

}
