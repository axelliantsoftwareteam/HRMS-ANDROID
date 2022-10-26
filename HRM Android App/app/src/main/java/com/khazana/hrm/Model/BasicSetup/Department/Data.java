
package com.khazana.hrm.Model.BasicSetup.Department;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetDepartmentData> getDepartmentData = null;

    public List<GetDepartmentData> getResponse() {
        return getDepartmentData;
    }

    public void setResponse(List<GetDepartmentData> getDepartmentData) {
        this.getDepartmentData = getDepartmentData;
    }

}
