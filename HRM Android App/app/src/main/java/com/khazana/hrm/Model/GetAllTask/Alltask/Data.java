
package com.khazana.hrm.Model.GetAllTask.Alltask;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetAlltaskData> getAlltaskData = null;
    @SerializedName("counts_info")
    @Expose
    private CountsInfo countsInfo;

    public List<GetAlltaskData> getResponse() {
        return getAlltaskData;
    }

    public void setResponse(List<GetAlltaskData> getAlltaskData) {
        this.getAlltaskData = getAlltaskData;
    }

    public CountsInfo getCountsInfo() {
        return countsInfo;
    }

    public void setCountsInfo(CountsInfo countsInfo) {
        this.countsInfo = countsInfo;
    }

}
