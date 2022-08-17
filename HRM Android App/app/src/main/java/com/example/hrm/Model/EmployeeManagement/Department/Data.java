
package com.example.hrm.Model.EmployeeManagement.Department;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetDepartData> getDepartData = null;

    public List<GetDepartData> getResponse() {
        return getDepartData;
    }

    public void setResponse(List<GetDepartData> getDepartData) {
        this.getDepartData = getDepartData;
    }

}
