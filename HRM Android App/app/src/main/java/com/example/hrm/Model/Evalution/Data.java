
package com.example.hrm.Model.Evalution;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetEvalutionData> getEvalutionData = null;

    public List<GetEvalutionData> getResponse() {
        return getEvalutionData;
    }

    public void setResponse(List<GetEvalutionData> getEvalutionData) {
        this.getEvalutionData = getEvalutionData;
    }

}
