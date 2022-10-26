
package com.khazana.hrm.Model.BasicSetup.Designation.GetAllDesign;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetAllDesignationData> getAllDesignationData = null;

    public List<GetAllDesignationData> getResponse() {
        return getAllDesignationData;
    }

    public void setResponse(List<GetAllDesignationData> getAllDesignationData) {
        this.getAllDesignationData = getAllDesignationData;
    }

}
