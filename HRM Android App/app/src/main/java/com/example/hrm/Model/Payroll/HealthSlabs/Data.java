
package com.example.hrm.Model.Payroll.HealthSlabs;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetHealthSlabData> getHealthSlabData = null;

    public List<GetHealthSlabData> getResponse() {
        return getHealthSlabData;
    }

    public void setResponse(List<GetHealthSlabData> getHealthSlabData) {
        this.getHealthSlabData = getHealthSlabData;
    }

}
