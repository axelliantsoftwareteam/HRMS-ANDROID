
package com.example.hrm.Model.EmployeeManagement;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetWorkFlowData> getWorkFlowData = null;

    public List<GetWorkFlowData> getResponse() {
        return getWorkFlowData;
    }

    public void setResponse(List<GetWorkFlowData> getWorkFlowData) {
        this.getWorkFlowData = getWorkFlowData;
    }

}
