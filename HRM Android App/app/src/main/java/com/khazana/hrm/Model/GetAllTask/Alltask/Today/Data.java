
package com.khazana.hrm.Model.GetAllTask.Alltask.Today;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetTodayData> getTodayData = null;
    @SerializedName("counts_info")
    @Expose
    private CountsInfo countsInfo;

    public List<GetTodayData> getResponse() {
        return getTodayData;
    }

    public void setResponse(List<GetTodayData> getTodayData) {
        this.getTodayData = getTodayData;
    }

    public CountsInfo getCountsInfo() {
        return countsInfo;
    }

    public void setCountsInfo(CountsInfo countsInfo) {
        this.countsInfo = countsInfo;
    }

}
