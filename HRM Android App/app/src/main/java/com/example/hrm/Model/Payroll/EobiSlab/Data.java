
package com.example.hrm.Model.Payroll.EobiSlab;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetEobiSlabData> getEobiSlabData = null;

    public List<GetEobiSlabData> getResponse() {
        return getEobiSlabData;
    }

    public void setResponse(List<GetEobiSlabData> getEobiSlabData) {
        this.getEobiSlabData = getEobiSlabData;
    }

}
