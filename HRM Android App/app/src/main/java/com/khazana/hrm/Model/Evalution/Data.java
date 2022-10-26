
package com.khazana.hrm.Model.Evalution;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetEvaluationData> getEvaluationData = null;

    public List<GetEvaluationData> getResponse() {
        return getEvaluationData;
    }

    public void setResponse(List<GetEvaluationData> getEvaluationData) {
        this.getEvaluationData = getEvaluationData;
    }

}
