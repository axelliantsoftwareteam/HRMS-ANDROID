
package com.khazana.hrm.Model.EmployeeManagement.Employee;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetEmployData> getEmployData = null;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public List<GetEmployData> getResponse() {
        return getEmployData;
    }

    public void setResponse(List<GetEmployData> getEmployData) {
        this.getEmployData = getEmployData;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}
